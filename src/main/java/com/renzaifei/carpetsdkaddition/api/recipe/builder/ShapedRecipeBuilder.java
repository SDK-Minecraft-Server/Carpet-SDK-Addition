
package com.renzaifei.carpetsdkaddition.api.recipe.builder;

import com.renzaifei.carpetsdkaddition.utils.ChainableHashMap;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShapedRecipeBuilder extends AbstractRecipeBuilder {
    private final List<String> patternRows = new ArrayList<>();
    private final Map<Character, Item> ingredients = new HashMap<>();

    private ShapedRecipeBuilder(boolean enabled, String recipeName) {
        super(enabled, recipeName);
    }

    public static ShapedRecipeBuilder create(boolean enabled, String recipeName) {
        return new ShapedRecipeBuilder(enabled, recipeName);
    }

    public ShapedRecipeBuilder pattern(String row) {
        if (row.length() != 3) {
            throw new IllegalArgumentException("Pattern row must be 3 characters");
        }
        patternRows.add(row);
        return this;
    }

    public ShapedRecipeBuilder define(char symbol, Item item) {
        ingredients.put(symbol, item);
        return this;
    }

    @Override
    public void build() {
        if (!enabled || resultItem == null) {
            return;
        }
        String[][] pattern = new String[patternRows.size()][];
        for (int i = 0; i < patternRows.size(); i++) {
            pattern[i] = patternRows.get(i).split("");
        }
        ChainableHashMap<Character, String> ingredientMap = new ChainableHashMap<>();
        ingredients.forEach((k, v) -> ingredientMap.cPut(k, item(v)));
        RecipeBuilder.getInstance().addShapedRecipe(recipeName, pattern, ingredientMap, item(resultItem), resultCount);
    }
}
