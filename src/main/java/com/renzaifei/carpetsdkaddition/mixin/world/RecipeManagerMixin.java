package com.renzaifei.carpetsdkaddition.mixin.world;


import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionExtension;
//#if MC>=260300
//$$ import net.minecraft.core.registries.Registries;
//$$ import net.minecraft.resources.FileToIdConverter;
//$$ import net.minecraft.server.packs.resources.Resource;
//#endif
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

//#if MC < 260300
@Mixin(RecipeManager.class)
//#else
//$$ @Mixin(FileToIdConverter.class)
//#endif
public abstract class RecipeManagerMixin {

    //#if MC < 12102
    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("HEAD")
    )
    private void onApply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
        CarpetSDKAdditionExtension.getInstance().registerCustomRecipes(map);
    }
    //#elseif MC < 260300
    //$$ @Inject(
    //$$         method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;",
    //$$         at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;<init>(I)V")
    //$$ )
    //$$ private void onPrepare(ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<RecipeMap> cir, @Local SortedMap<ResourceLocation, Recipe<?>> sortedMap) {
    //$$     CarpetSDKAdditionExtension.getInstance().registerCustomRecipes(sortedMap);
    //$$ }
    //#else
    //$$ @Inject(method = "listMatchingResources", at = @At("RETURN"), cancellable = true)
    //$$ private void addCustomRecipes(ResourceManager resourceManager, CallbackInfoReturnable<Map<Identifier, Resource>> cir) {
    //$$     FileToIdConverter converter = (FileToIdConverter) (Object) this;
    //$$     if (converter.prefix().equals(Registries.elementsDirPath(Registries.RECIPE))) {
    //$$         cir.setReturnValue(CarpetSDKAdditionExtension.getInstance().registerCustomRecipeResources(converter, resourceManager, cir.getReturnValue()));
    //$$     }
    //$$ }
    //#endif
}
