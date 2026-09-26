package com.renzaifei.carpetsdkaddition.mixin.world;

import com.google.common.collect.Lists;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionSettings;
import net.minecraft.Util;
import net.minecraft.server.level.*;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;


@Mixin(ServerChunkCache.class)
public abstract class ServerChunkCacheMixin extends ChunkSource {
    //#if MC <= 12101

    @Shadow
    @Final
    ServerLevel level;

    @Shadow
    @Final
    public ChunkMap chunkMap;

    @Shadow
    private boolean spawnEnemies;
    @Shadow
    private boolean spawnFriendlies;

    @Shadow
    private long lastInhabitedUpdate;

    @Shadow
    @Final
    private DistanceManager distanceManager;

    @Shadow
    private NaturalSpawner.SpawnState lastSpawnState;

    @Shadow
    private void getFullChunk(long l, Consumer<LevelChunk> consumer){}


    @Inject(
            method = "tickChunks",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onTickChunks(CallbackInfo ci) {
        if (!CarpetSDKAdditionSettings.noPlayerRandomTick) return;
        long l = this.level.getGameTime();
        long m = l - this.lastInhabitedUpdate;
        this.lastInhabitedUpdate = l;
        if (!this.level.isDebug()) {
            ProfilerFiller profilerFiller = this.level.getProfiler();
            profilerFiller.push("pollingChunks");
            profilerFiller.push("filteringLoadedChunks");
            List<ServerChunkCache.ChunkAndHolder> list = Lists.newArrayListWithCapacity(this.chunkMap.size());

            for(ChunkHolder chunkHolder : this.chunkMap.getChunks()) {
                LevelChunk levelChunk = chunkHolder.getTickingChunk();
                if (levelChunk != null) {
                    list.add(new ServerChunkCache.ChunkAndHolder(levelChunk, chunkHolder));
                }
            }

            if (this.level.tickRateManager().runsNormally()) {
                profilerFiller.popPush("naturalSpawnCount");
                int i = this.distanceManager.getNaturalSpawnChunkCount();
                NaturalSpawner.SpawnState spawnState = NaturalSpawner.createState(i, this.level.getAllEntities(), this::getFullChunk, new LocalMobCapCalculator(this.chunkMap));
                this.lastSpawnState = spawnState;
                profilerFiller.popPush("spawnAndTick");
                boolean bl = this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING);
                Util.shuffle(list, this.level.random);
                int j = this.level.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
                boolean bl2 = this.level.getLevelData().getGameTime() % 400L == 0L;

                for(ServerChunkCache.ChunkAndHolder chunkAndHolder : list) {
                    LevelChunk levelChunk2 = chunkAndHolder.chunk();
                    ChunkPos chunkPos = levelChunk2.getPos();
                    if (this.level.isNaturalSpawningAllowed(chunkPos)) {
                        levelChunk2.incrementInhabitedTime(m);
                        if (this.chunkMap.anyPlayerCloseEnoughForSpawning(chunkPos)) {
                            if (bl && (this.spawnEnemies || this.spawnFriendlies) && this.level.getWorldBorder().isWithinBounds(chunkPos)) {
                                NaturalSpawner.spawnForChunk(this.level, levelChunk2, spawnState, this.spawnFriendlies, this.spawnEnemies, bl2);
                            }
                        }

                        if (this.level.shouldTickBlocksAt(chunkPos.toLong())) {
                            this.level.tickChunk(levelChunk2, j);
                        }
                    }
                }

                profilerFiller.popPush("customSpawners");
                if (bl) {
                    this.level.tickCustomSpawners(this.spawnEnemies, this.spawnFriendlies);
                }
            }

            profilerFiller.popPush("broadcast");
            list.forEach((chunkAndHolderx) -> chunkAndHolderx.holder().broadcastChanges(chunkAndHolderx.chunk()));
            profilerFiller.pop();
            profilerFiller.pop();
        }
        ci.cancel();
    }

    //#elseif MC > 12101 && MC <12105
    //$$
    //$$ @Final
    //$$ @Shadow
    //$$ private ServerLevel level;
    //$$
    //$$ @Final
    //$$ @Shadow
    //$$ public ChunkMap chunkMap;
    //$$
    //$$ @Shadow
    //$$ private boolean spawnEnemies;
    //$$ @Shadow
    //$$ private boolean spawnFriendlies;
    //$$
    //$$
    //$$ @Inject(
    //$$         method = "tickChunks(Lnet/minecraft/util/profiling/ProfilerFiller;JLjava/util/List;)V",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Ljava/util/List;iterator()Ljava/util/Iterator;"),
    //$$         cancellable = true
    //$$ )
    //$$ private void onTickChunks(ProfilerFiller profilerFiller,
    //$$                           long l,
    //$$                           List<LevelChunk> list,
    //$$                           CallbackInfo ci,
    //$$                           @Local NaturalSpawner.SpawnState spawnState,
    //$$                           @Local(ordinal = 1) List<MobCategory> list2,
    //$$                           @Local(ordinal = 1) int j,
    //$$                           @Local(ordinal = 0) boolean bl
    //$$                           ) {
    //$$     if (!CarpetSDKAdditionSettings.noPlayerRandomTick) return;
    //$$     for (LevelChunk levelChunk : list) {
    //$$         ChunkPos chunkPos = levelChunk.getPos();
    //$$         levelChunk.incrementInhabitedTime(l);
    //$$         if (!list2.isEmpty() && this.level.getWorldBorder().isWithinBounds(chunkPos)) {
    //$$             NaturalSpawner.spawnForChunk(this.level, levelChunk, spawnState, list2);
    //$$         }
    //$$     }
    //$$     for (ChunkHolder holder : this.chunkMap.getChunks()) {
    //$$         LevelChunk chunk = holder.getTickingChunk();
    //$$         if (chunk != null && this.level.shouldTickBlocksAt(chunk.getPos().toLong())) {
    //$$             this.level.tickChunk(chunk, j);
    //$$         }
    //$$     }
    //$$
    //$$     profilerFiller.popPush("customSpawners");
    //$$     if (bl) {
    //$$         this.level.tickCustomSpawners(this.spawnEnemies, this.spawnFriendlies);
    //$$     }
    //$$     ci.cancel();
    //$$ }
    //#endif
}
