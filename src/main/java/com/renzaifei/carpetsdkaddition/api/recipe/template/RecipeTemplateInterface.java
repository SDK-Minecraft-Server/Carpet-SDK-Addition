package com.renzaifei.carpetsdkaddition.api.recipe.template;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Map;
import java.util.SortedMap;

public interface RecipeTemplateInterface {
    //#if MC<12102
    JsonObject toJson();
    //#else
    //$$ Recipe<?> toRecipe();
    //#endif


    ResourceLocation getRecipeId();

    //#if MC<12102
    default void addToRecipeMap(Map<ResourceLocation, JsonElement> recipeMap) {
       recipeMap.put(getRecipeId(), toJson());
    };
    //#else
    //$$ default void addToRecipeMap(SortedMap<ResourceLocation, Recipe<?>> recipeMap) {
    //$$     recipeMap.put(getRecipeId(), toRecipe());
    //$$ }
    //#endif
}
