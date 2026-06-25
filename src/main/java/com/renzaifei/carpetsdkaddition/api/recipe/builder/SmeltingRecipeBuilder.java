
package com.renzaifei.carpetsdkaddition.api.recipe.builder;


import net.minecraft.world.item.Item;

public class SmeltingRecipeBuilder extends AbstractRecipeBuilder {
    private Item material;
    private float experience;
    private int cookingTime;

    private SmeltingRecipeBuilder(boolean enabled, String recipeName) {
        super(enabled, recipeName);
    }

    public static SmeltingRecipeBuilder create(boolean enabled, String recipeName) {
        return new SmeltingRecipeBuilder(enabled, recipeName);
    }

    public SmeltingRecipeBuilder material(Item item) {
        this.material = item;
        return this;
    }

    public SmeltingRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }

    public SmeltingRecipeBuilder cookTime(int ticks) {
        this.cookingTime = ticks;
        return this;
    }

    @Override
    public void build() {
        if (!enabled || resultItem == null || material == null) {
            return;
        }
        RecipeBuilder.getInstance().addSmeltingRecipe(recipeName, item(material), item(resultItem), experience, cookingTime);
    }
}
