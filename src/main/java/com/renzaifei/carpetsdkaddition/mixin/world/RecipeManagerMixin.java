package com.renzaifei.carpetsdkaddition.mixin.world;


import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import com.renzaifei.carpetsdkaddition.CarpetSDKAddition;
import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
//#if MC>=12102
//$$ import net.minecraft.world.item.crafting.RecipeMap;
//#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.SortedMap;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    //#if MC < 12102
    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("HEAD")
    )
    private void onApply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
        CarpetSDKAdditionExtension.getInstance().registerCustomRecipes(map);
    }
    //#else
    //$$ @Inject(
    //$$         method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;",
    //$$         at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;<init>(I)V")
    //$$ )
    //$$ private void onPrepare(ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<RecipeMap> cir, @Local SortedMap<ResourceLocation, Recipe<?>> sortedMap) {
    //$$     CarpetSDKAdditionExtension.getInstance().registerCustomRecipes(sortedMap);
    //$$ }
    //#endif
}
