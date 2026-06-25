
package com.renzaifei.carpetsdkaddition.api.recipe.builder;

import com.renzaifei.carpetsdkaddition.utils.ChainableList;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipeBuilder extends AbstractRecipeBuilder {
    private final List<Item> ingredients = new ArrayList<>();

    private ShapelessRecipeBuilder(boolean enabled, String recipeName) {
        super(enabled, recipeName);
    }

    public static ShapelessRecipeBuilder create(boolean enabled, String recipeName) {
        return new ShapelessRecipeBuilder(enabled, recipeName);
    }

    public ShapelessRecipeBuilder addIngredient(Item item) {
        ingredients.add(item);
        return this;
    }

    @Override
    public void build() {
        if (!enabled || resultItem == null) {
            return;
        }
        ChainableList<String> ingredientList = new ChainableList<>();
        ingredients.forEach(item -> ingredientList.cAdd(item(item)));
        RecipeBuilder.getInstance().addShapelessRecipe(recipeName, ingredientList, item(resultItem), resultCount);
    }
}
