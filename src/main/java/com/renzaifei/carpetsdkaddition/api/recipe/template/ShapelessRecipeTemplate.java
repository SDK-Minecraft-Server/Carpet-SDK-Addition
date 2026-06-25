
package com.renzaifei.carpetsdkaddition.api.recipe.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

//#if MC>=260100
//$$ import net.minecraft.world.item.ItemStackTemplate;
//#endif
import net.minecraft.world.item.crafting.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ShapelessRecipeTemplate implements RecipeTemplateInterface {
    private final ResourceLocation recipeId;
    private final List<String> ingredients;
    private final String resultItem;
    private final int resultCount;

    public ShapelessRecipeTemplate(ResourceLocation recipeId, List<String> ingredients, String resultItem, int resultCount) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.resultItem = resultItem;
        this.resultCount = resultCount;
    }

    //#if MC<12102
    @Override
    public JsonObject toJson() {
        JsonObject recipeJson = new JsonObject();
        recipeJson.addProperty("type", "minecraft:crafting_shapeless");

        JsonArray ingredientsJson = new JsonArray();
        for (String ingredient : ingredients) {
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("item", ingredient);
            ingredientsJson.add(itemJson);
        }
        recipeJson.add("ingredients", ingredientsJson);

        JsonObject resultJson = new JsonObject();
        resultJson.addProperty("id", resultItem);
        resultJson.addProperty("count", resultCount);
        recipeJson.add("result", resultJson);
        return recipeJson;
    }

    //#else
    //$$ @Override
    //$$ public ShapelessRecipe toRecipe() {
    //$$     Item item = BuiltInRegistries.ITEM.getValue(ResourceLocation.parse(resultItem));
    //$$     ItemStack result = new ItemStack(item, resultCount);
    //$$     System.out.println(result);
    //$$     List<Ingredient> list = new ArrayList<>();
    //$$     for (String id : ingredients) {
    //$$         list.add(Ingredient.of(BuiltInRegistries.ITEM.getValue(ResourceLocation.parse(id))));
    //$$     }
        //#if MC>=260100
        //$$ return new ShapelessRecipe(new Recipe.CommonInfo(true),new CraftingRecipe.CraftingBookInfo(CraftingBookCategory.MISC,""), new ItemStackTemplate(item), list);
        //#else
        //$$ return new ShapelessRecipe("", CraftingBookCategory.MISC, result, list);
        //#endif
    //$$ }
    //#endif

    @Override
    public ResourceLocation getRecipeId() {
        return recipeId;
    }



}
