package com.renzaifei.carpetsdkaddition;

import carpet.CarpetExtension;
import com.renzaifei.carpetsdkaddition.api.recipe.builder.RecipeBuilder;
import com.renzaifei.carpetsdkaddition.api.recipe.RecipeManager;
import com.renzaifei.carpetsdkaddition.rules.CustomRecipes;
import com.renzaifei.carpetsdkaddition.utils.CarpetSDKAdditionTranslations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import com.google.gson.JsonElement;

import java.util.Map;
import java.util.SortedMap;

import static carpet.CarpetServer.settingsManager;


public class CarpetSDKAdditionExtension implements CarpetExtension {
    private static final CarpetSDKAdditionExtension INSTANCE = new CarpetSDKAdditionExtension();


    public static CarpetSDKAdditionExtension getInstance() {
        return INSTANCE;
    }

    @Override
    public void onGameStarted() {
        settingsManager.parseSettingsClass(CarpetSDKAdditionSettings.class);
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return CarpetSDKAdditionTranslations.getTranslations(lang);
    }

    @Override
    public String version() {
        return CarpetSDKAddition.version;
    }


    public void registerCustomRecipes(
    //#if MC<12102
            Map<ResourceLocation, JsonElement> map
    //#else
    //$$             SortedMap<ResourceLocation, Recipe<?>> map
    //#endif
    ) {
        RecipeManager recipeManager = new RecipeManager(RecipeBuilder.getInstance());
        RecipeManager.clearRecipeListMemory(RecipeBuilder.getInstance());
        CustomRecipes.getInstance().buildRecipes();
        recipeManager.registerRecipes(map);
    }



}
