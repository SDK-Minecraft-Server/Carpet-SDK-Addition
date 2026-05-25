package com.renzaifei.carpetsdkaddition.mixin.world;

import com.llamalad7.mixinextras.expression.Definition;import com.llamalad7.mixinextras.expression.Expression;import com.llamalad7.mixinextras.sugar.Local;
import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionSettings;
import net.minecraft.Util;
import net.minecraft.server.level.*;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
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

import java.util.List;


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

    @Redirect(
            method = "tickChunks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;isNaturalSpawningAllowed(Lnet/minecraft/world/level/ChunkPos;)Z",
                    ordinal = 0
            )
    )
    private boolean redirectIsNaturalSpawningAllowed(ServerLevel instance, ChunkPos chunkPos) {
        if (!CarpetSDKAdditionSettings.noPlayerRandomTick) {
           return this.level.isNaturalSpawningAllowed(chunkPos) && this.chunkMap.anyPlayerCloseEnoughForSpawning(chunkPos);
        } else {
            return false;
        }
    }

    @Definition(id = "getPos",method="Lnet/minecraft/world/level/chunk/LevelChunk;getPos()Lnet/minecraft/world/level/ChunkPos;")@Expression("? = ?.getPos()")@Inject(
            method = "tickChunks",
            at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER)
    )
    private void onGetPos(CallbackInfo ci ,
                          @Local(name = "chunkPos") ChunkPos chunkPos,
                          @Local(name = "levelChunk2") LevelChunk levelChunk2,
                          @Local(name = "m") long m,
                          @Local(name = "bl") boolean bl,
                          @Local(name = "spawnState") NaturalSpawner.SpawnState spawnState,
                          @Local(name = "bl2") boolean bl2,
                          @Local(name = "j") int j
                          ) {
        if (!CarpetSDKAdditionSettings.noPlayerRandomTick) return;
        if (this.level.isNaturalSpawningAllowed(chunkPos)) {
            levelChunk2.incrementInhabitedTime(m);

            if (this.chunkMap.anyPlayerCloseEnoughForSpawning(chunkPos) && bl && (this.spawnEnemies || this.spawnFriendlies) && this.level.getWorldBorder().isWithinBounds(chunkPos)) {
                NaturalSpawner.spawnForChunk(this.level, levelChunk2, spawnState, this.spawnFriendlies, this.spawnEnemies, bl2);
            }

            if (this.level.shouldTickBlocksAt(chunkPos.toLong())) {
                this.level.tickChunk(levelChunk2, j);
            }
        }
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
