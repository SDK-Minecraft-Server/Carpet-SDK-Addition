package com.renzaifei.carpetsdkaddition.mixin.entity;

import com.llamalad7.mixinextras.sugar.Local;
import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Projectile.class)
public abstract class ThrowableProjectileMixin {

    //#if MC <= 12101
    @Shadow
    @Nullable
    private Entity cachedOwner;

    @Shadow
    @Nullable
    private UUID ownerUUID;

    @Inject(
            method = "getOwner",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getEntity(Ljava/util/UUID;)Lnet/minecraft/world/entity/Entity;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true)
    private void getOwner(CallbackInfoReturnable<Entity> cir, @Local ServerLevel serverLevel){
        if (!CarpetSDKAdditionSettings.fixEnderPearlTeleport) return;
        if ((Object)this instanceof ThrownEnderpearl) {
            this.cachedOwner = serverLevel.getServer().getPlayerList().getPlayer(this.ownerUUID);
            cir.setReturnValue(this.cachedOwner);
        }
    }
    //#endif
}
