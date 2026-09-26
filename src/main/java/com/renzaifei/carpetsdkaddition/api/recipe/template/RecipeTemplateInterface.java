package com.renzaifei.carpetsdkaddition.api.recipe.template;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Map;
import java.util.SortedMap;

public interface RecipeTemplateInterface {
    //#if MC<12102 || MC>=260300
    //$$ JsonObject toJson();
    //#else
    Recipe<?> toRecipe();
    //#endif


    Identifier getRecipeId();

    //#if MC<12102 || MC>=260300
    //$$ default void addToRecipeMap(Map<Identifier, JsonElement> recipeMap) {
    //$$    recipeMap.put(getRecipeId(), toJson());
    //$$ }
    //#else
    default void addToRecipeMap(SortedMap<Identifier, Recipe<?>> recipeMap) {
        recipeMap.put(getRecipeId(), toRecipe());
    }
    //#endif
}
