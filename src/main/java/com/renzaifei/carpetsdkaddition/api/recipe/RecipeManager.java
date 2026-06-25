
package com.renzaifei.carpetsdkaddition.api.recipe;


import com.google.gson.JsonElement;
import com.renzaifei.carpetsdkaddition.api.recipe.builder.RecipeBuilder;
import com.renzaifei.carpetsdkaddition.api.recipe.template.ShapedRecipeTemplate;
import com.renzaifei.carpetsdkaddition.api.recipe.template.ShapelessRecipeTemplate;
import com.renzaifei.carpetsdkaddition.api.recipe.template.SmeltingRecipeTemplate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class RecipeManager {
    private final List<ShapelessRecipeTemplate> shapelessRecipes;
    private final List<ShapedRecipeTemplate> shapedRecipes;
    private final List<SmeltingRecipeTemplate> smeltingRecipes;

    public RecipeManager(RecipeBuilder builder) {
        this.shapelessRecipes = builder.getShapelessRecipeList();
        this.shapedRecipes = builder.getShapedRecipeList();
        this.smeltingRecipes = builder.getSmeltingRecipeList();
    }

    //#if MC<12102
    public void registerRecipes(Map<ResourceLocation, JsonElement> recipeMap) {
    //#else
    //$$ public void registerRecipes(SortedMap<ResourceLocation, Recipe<?>> recipeMap) {
    //#endif
        registerAllRecipes(recipeMap);
    }




    //#if MC<12102
    private void registerAllRecipes(Map<ResourceLocation, JsonElement> recipeMap) {
    //#else
    //$$ private void registerAllRecipes(SortedMap<ResourceLocation, Recipe<?>> recipeMap) {
    //#endif
        shapelessRecipes.forEach(recipe -> recipe.addToRecipeMap(recipeMap));
        shapedRecipes.forEach(recipe -> recipe.addToRecipeMap(recipeMap));
        smeltingRecipes.forEach(recipe -> recipe.addToRecipeMap(recipeMap));
    }


    public static void clearRecipeListMemory(RecipeBuilder recipeBuilder) {
        recipeBuilder.getShapedRecipeList().clear();
        recipeBuilder.getShapelessRecipeList().clear();
        recipeBuilder.getSmeltingRecipeList().clear();
    }
}
