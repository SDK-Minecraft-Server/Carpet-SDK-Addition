package com.renzaifei.carpetsdkaddition.rules;


import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionSettings;
import com.renzaifei.carpetsdkaddition.api.recipe.builder.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

//参考了ams的代码
public class CustomRecipes {
    private static final CustomRecipes INSTANCE = new CustomRecipes();

    private CustomRecipes(){}

    public static CustomRecipes getInstance() {return INSTANCE;}

    public void buildRecipes() {
        //龙息合成
        ShapelessRecipeBuilder.create(CarpetSDKAdditionSettings.craftableDragonBreath,"dragon_breath")
                .addIngredient(Items.EXPERIENCE_BOTTLE)
                .addIngredient(Items.CHORUS_FRUIT)
                .addIngredient(Items.BLAZE_POWDER)
                .output(Items.DRAGON_BREATH, 1)
                .build();

    }
}
