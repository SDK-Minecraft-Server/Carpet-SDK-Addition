
package com.renzaifei.carpetsdkaddition.api.recipe.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
//#if MC>=260100
//$$ import net.minecraft.world.item.ItemStackTemplate;
//#endif
import net.minecraft.world.item.crafting.Recipe;

import java.util.*;

import net.minecraft.world.item.crafting.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class ShapedRecipeTemplate implements RecipeTemplateInterface {
    private final ResourceLocation recipeId;
    private final String[][] pattern;
    private final Map<Character, String> ingredients;
    private final String resultItem;
    private final int resultCount;

    public ShapedRecipeTemplate(ResourceLocation recipeId, String[][] pattern, Map<Character, String> ingredients, String resultItem, int resultCount) {
        this.recipeId = recipeId;
        this.pattern = pattern;
        this.ingredients = ingredients;
        this.resultItem = resultItem;
        this.resultCount = resultCount;
    }

    //#if MC<12102
    @Override
    public JsonObject toJson() {
       JsonObject recipeJson = new JsonObject();
       recipeJson.addProperty("type", "minecraft:crafting_shaped");

       JsonArray patternJson = new JsonArray();
       for (String[] row : pattern) {
           StringBuilder rowString = new StringBuilder();
           for (String cell : row) {
               rowString.append(cell);
           }
           patternJson.add(rowString.toString());
       }
       recipeJson.add("pattern", patternJson);

       JsonObject keyJson = new JsonObject();
       for (Map.Entry<Character, String> entry : ingredients.entrySet()) {
           JsonObject itemJson = new JsonObject();
           itemJson.addProperty("item", entry.getValue());
           keyJson.add(entry.getKey().toString(), itemJson);
        }
       recipeJson.add("key", keyJson);

       JsonObject resultJson = new JsonObject();
       resultJson.addProperty("id", resultItem);
       resultJson.addProperty("count", resultCount);
       recipeJson.add("result", resultJson);

       return recipeJson;
    }

    //#else
    //$$ @Override
    //$$ public Recipe<?> toRecipe() {
    //$$     Item item = BuiltInRegistries.ITEM.getValue(ResourceLocation.parse(resultItem));
    //$$     ItemStack result = new ItemStack(item, resultCount);
    //$$     int w = pattern[0].length;
    //$$     int h = pattern.length;
    //$$     List<Optional<Ingredient>> list = new ArrayList<>();
    //$$     for (String[] row : pattern) {
    //$$         for (String cell : row) {
    //$$             char c = cell.charAt(0);
    //$$             String id = ingredients.get(c);
    //$$             if (id != null) {
    //$$                 Item ingredientItem = BuiltInRegistries.ITEM.getValue(ResourceLocation.parse(id));
    //$$                 list.add(Optional.of(Ingredient.of(ingredientItem)));
    //$$             } else {
    //$$                 list.add(Optional.empty());
    //$$             }
    //$$         }
    //$$     }
    //$$     ShapedRecipePattern pat = new ShapedRecipePattern(w, h, list, Optional.empty());
        //#if MC>=260100
        //$$ return new ShapedRecipe(new Recipe.CommonInfo(true),new CraftingRecipe.CraftingBookInfo(CraftingBookCategory.MISC,""), pat, new ItemStackTemplate(item));
        //#else
        //$$ return new ShapedRecipe("", CraftingBookCategory.MISC, pat, result);
        //#endif
    //$$ }
    //#endif

    @Override
    public ResourceLocation getRecipeId() {
        return recipeId;
    }

}
