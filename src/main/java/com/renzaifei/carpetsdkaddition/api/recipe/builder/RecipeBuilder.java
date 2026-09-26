
package com.renzaifei.carpetsdkaddition.api.recipe.builder;

import com.renzaifei.carpetsdkaddition.CarpetSDKAddition;
import com.renzaifei.carpetsdkaddition.api.recipe.template.ShapedRecipeTemplate;
import com.renzaifei.carpetsdkaddition.api.recipe.template.ShapelessRecipeTemplate;
import com.renzaifei.carpetsdkaddition.api.recipe.template.SmeltingRecipeTemplate;
import com.renzaifei.carpetsdkaddition.utils.ChainableHashMap;
import com.renzaifei.carpetsdkaddition.utils.ChainableList;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {
    private static final RecipeBuilder INSTANCE = new RecipeBuilder();
    private static final List<ShapedRecipeTemplate> shapedRecipeList = new ArrayList<>();
    private static final List<ShapelessRecipeTemplate> shapelessRecipeList = new ArrayList<>();
    private static final List<SmeltingRecipeTemplate> smeltingRecipeList = new ArrayList<>();

    private RecipeBuilder() {}

    public static RecipeBuilder getInstance() {
        return INSTANCE;
    }

    public List<ShapedRecipeTemplate> getShapedRecipeList() {
        return shapedRecipeList;
    }

    public List<ShapelessRecipeTemplate> getShapelessRecipeList() {
        return shapelessRecipeList;
    }

    public List<SmeltingRecipeTemplate> getSmeltingRecipeList() {
        return smeltingRecipeList;
    }

    public void addShapedRecipe(String id, String[][] pattern, ChainableHashMap<Character, String> ingredients, String result, int count) {
        shapedRecipeList.add(new ShapedRecipeTemplate(ResourceLocation.fromNamespaceAndPath(CarpetSDKAddition.MOD_ID,id), pattern, ingredients, result, count));
    }

    public void addShapelessRecipe(String id, ChainableList<String> ingredients, String result, int count) {
        shapelessRecipeList.add(new ShapelessRecipeTemplate(ResourceLocation.fromNamespaceAndPath(CarpetSDKAddition.MOD_ID,id), ingredients, result, count));
    }

    public void addSmeltingRecipe(String id, String input, String output, float experience, int cookingTime) {
        smeltingRecipeList.add(new SmeltingRecipeTemplate(ResourceLocation.fromNamespaceAndPath(CarpetSDKAddition.MOD_ID,id), input, output, experience, cookingTime));
    }
}
