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
//#if MC>=260300
//$$ import net.minecraft.resources.FileToIdConverter;
//$$ import net.minecraft.server.packs.PackResources;
//$$ import net.minecraft.server.packs.resources.Resource;
//$$ import net.minecraft.server.packs.resources.ResourceManager;
//$$ import java.io.ByteArrayInputStream;
//$$ import java.nio.charset.StandardCharsets;
//$$ import java.util.HashMap;
//#endif

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
        return CarpetSDKAddition.MOD_ID;
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayer player) {
        CarpetExtension.super.onPlayerLoggedIn(player);
        RecipeRuleHelper.onPlayerLoggedId(minecraftServer,player);
    }

    public void registerCustomRecipes(
    //#if MC<12102
            Map<ResourceLocation, JsonElement> map
    //#elseif MC < 260300
    //$$             SortedMap<ResourceLocation, Recipe<?>> map
    //#else
    //$$         Map<Identifier, JsonElement> map
    //#endif
    ) {
        RecipeManager recipeManager = new RecipeManager(RecipeBuilder.getInstance());
        RecipeManager.clearRecipeListMemory(RecipeBuilder.getInstance());
        CustomRecipes.getInstance().buildRecipes();
        recipeManager.registerRecipes(map);
    }

    //#if MC>=260300
    //$$ public Map<Identifier, Resource> registerCustomRecipeResources(FileToIdConverter converter, ResourceManager resourceManager, Map<Identifier, Resource> original) {
    //$$     Map<Identifier, JsonElement> customRecipes = new HashMap<>();
    //$$     registerCustomRecipes(customRecipes);
    //$$     if (customRecipes.isEmpty()) {
    //$$         return original;
    //$$     }
    //$$     PackResources source = original.values().stream().findFirst().map(Resource::source)
    //$$             .orElseGet(() -> resourceManager.listPacks().findFirst()
    //$$                     .orElseThrow(() -> new IllegalStateException("No resource pack available for custom recipes")));
    //$$     Map<Identifier, Resource> recipes = new HashMap<>(original);
    //$$     customRecipes.forEach((id, json) -> {
    //$$         byte[] contents = json.toString().getBytes(StandardCharsets.UTF_8);
    //$$         recipes.put(converter.idToFile(id), new Resource(source, () -> new ByteArrayInputStream(contents)));
    //$$     });
    //$$     return recipes;
    //$$ }
    //#endif
}
