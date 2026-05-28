package com.renzaifei.carpetsdkaddition.mixin.entity;

import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TheEndGatewayBlockEntity.class)
public abstract class TheEndGatewayBlockEntityMixin {
    @Shadow
    public int teleportCooldown;

    @Inject(method = "triggerCooldown", at = @At("TAIL"))
    private static void onTriggerCooldown(Level level, BlockPos blockPos, BlockState blockState, TheEndGatewayBlockEntity theEndGatewayBlockEntity, CallbackInfo ci) {
        theEndGatewayBlockEntity.teleportCooldown = CarpetSDKAdditionSettings.modifiableEndGatewayCooldown;
    }

    @Inject(method = "triggerEvent" , at = @At("HEAD"), cancellable = true)
    private void onTriggerEvent(int i, int j, CallbackInfoReturnable<Boolean> cir) {
        if (i == 1) {
            this.teleportCooldown = CarpetSDKAdditionSettings.modifiableEndGatewayCooldown;
            cir.setReturnValue(true);
        }
    }
}
