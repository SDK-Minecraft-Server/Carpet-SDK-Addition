package com.renzaifei.carpetsdkaddition.mixin.entity;


import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LecternBlockEntity.class)
public abstract class LecternBlockEntityMixin{

    //#if MC >12104
    //$$ @Inject(
    //$$         method = "preRemoveSideEffects",
    //$$         at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",shift = At.Shift.AFTER)
    //$$ )
    //$$ private void onPreRemoveSideEffects(BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
    //$$     if (!CarpetSDKAdditionSettings.fixLecternStateUpdate) return;
    //$$     assert ((LecternBlockEntity) (Object) this).getLevel() != null;
    //$$     LecternBlockEntity entity = (LecternBlockEntity) ((LecternBlockEntity) (Object) this).getLevel().getBlockEntity(blockPos);
    //$$     assert entity != null;
    //$$     entity.clearContent();
    //$$ }
    //#endif
}
