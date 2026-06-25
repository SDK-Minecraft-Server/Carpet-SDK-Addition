
package com.renzaifei.carpetsdkaddition.api.recipe.template;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//#if MC>=260100
//$$ import net.minecraft.world.item.ItemStackTemplate;
//#endif
import net.minecraft.world.item.crafting.*;

public class SmeltingRecipeTemplate implements RecipeTemplateInterface {
    private final ResourceLocation recipeId;
    private final String ingredient;
    private final String resultItem;
    private final float experience;
    private final int cookingTime;

    public SmeltingRecipeTemplate(ResourceLocation recipeId, String ingredient, String resultItem, float experience, int cookingTime) {
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.resultItem = resultItem;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    //#if MC<12102
    @Override
    public JsonObject toJson() {
        JsonObject recipeJson = new JsonObject();
        recipeJson.addProperty("type", "minecraft:smelting");

        JsonObject ingredientJson = new JsonObject();
        ingredientJson.addProperty("item", ingredient);
        recipeJson.add("ingredient", ingredientJson);
        JsonObject resultJson = new JsonObject();
        resultJson.addProperty("id", resultItem);
        recipeJson.add("result", resultJson);
        recipeJson.addProperty("experience", experience);
        recipeJson.addProperty("cookingtime", cookingTime);

        return recipeJson;
    }

    //#else
    //$$ @Override
    //$$ public SmeltingRecipe toRecipe() {
    //$$     Item in = BuiltInRegistries.ITEM.getValue(ResourceLocation.parse(ingredient));
    //$$     Item out = BuiltInRegistries.ITEM.getValue(ResourceLocation.parse(resultItem));
        //#if MC>=260100
        //$$ return new SmeltingRecipe(new Recipe.CommonInfo(true), new AbstractCookingRecipe.CookingBookInfo(CookingBookCategory.MISC,""), Ingredient.of(in), new ItemStackTemplate(out), experience, cookingTime);
        //#else
        //$$ return new SmeltingRecipe("", CookingBookCategory.MISC, Ingredient.of(in), new ItemStack(out), experience, cookingTime);
        //#endif
    //$$ }
    //#endif

    @Override
    public ResourceLocation getRecipeId() {
        return recipeId;
    }



}
