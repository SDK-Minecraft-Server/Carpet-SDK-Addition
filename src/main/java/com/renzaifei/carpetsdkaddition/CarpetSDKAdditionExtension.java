package com.renzaifei.carpetsdkaddition;

import carpet.CarpetExtension;
import com.renzaifei.carpetsdkaddition.api.recipe.RecipeRuleHelper;
import com.renzaifei.carpetsdkaddition.api.recipe.builder.RecipeBuilder;
import com.renzaifei.carpetsdkaddition.api.recipe.RecipeManager;
import com.renzaifei.carpetsdkaddition.rules.CustomRecipes;
import com.renzaifei.carpetsdkaddition.utils.CarpetSDKAdditionTranslations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import com.google.gson.JsonElement;

import java.util.Map;
import java.util.SortedMap;

import static carpet.CarpetServer.settingsManager;


public class CarpetSDKAdditionExtension implements CarpetExtension {
    private static final CarpetSDKAdditionExtension INSTANCE = new CarpetSDKAdditionExtension();
    private static MinecraftServer minecraftServer;


    public MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }
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
    public void onServerLoaded(MinecraftServer server) {
        CarpetExtension.super.onServerLoaded(server);
        minecraftServer = server;
    }

    @Override
    public String version() {
        return CarpetSDKAddition.version;
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayer player) {
        CarpetExtension.super.onPlayerLoggedIn(player);
        RecipeRuleHelper.onPlayerLoggedId(minecraftServer,player);
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
