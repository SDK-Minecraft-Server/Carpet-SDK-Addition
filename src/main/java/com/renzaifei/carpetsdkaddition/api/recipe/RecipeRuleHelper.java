package com.renzaifei.carpetsdkaddition.api.recipe;

import com.renzaifei.carpetsdkaddition.CarpetSDKAddition;
import com.renzaifei.carpetsdkaddition.CarpetSDKAdditionSettings;
import com.renzaifei.carpetsdkaddition.api.recipe.builder.RecipeBuilder;
import com.renzaifei.carpetsdkaddition.rules.CustomRecipes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class RecipeRuleHelper {

    private RecipeRuleHelper() {}

    public static void onValueChange(MinecraftServer server) {
        if (server == null || !server.isRunning()) {
            CarpetSDKAddition.LOGGER.warn("server is null or not running, skipping reload");
            return;
        }
        //#if MC<12102
        RecipeManager.clearRecipeListMemory(RecipeBuilder.getInstance());
        CustomRecipes.getInstance().buildRecipes();
        //#endif
        server.execute(() -> {
            //#if MC>=12102
            //$$ RecipeManager.clearRecipeListMemory(RecipeBuilder.getInstance());
            //$$ CustomRecipes.getInstance().buildRecipes();
            //#endif
            server.reloadResources(server.getPackRepository().getAvailableIds());
            Collection<RecipeHolder<?>> recipes = server.getRecipeManager().getRecipes();
            for (RecipeHolder<?> recipe : recipes) {
                //#if MC<12102
                if (recipe.id().getNamespace().equals(CarpetSDKAddition.MOD_ID)) {
                //#elseif MC<260100
                //$$ if (recipe.id().location().getNamespace().equals(CarpetSDKAddition.MOD_ID)) {
                //#else
                //$$ if (recipe.id().identifier().getNamespace().equals(CarpetSDKAddition.MOD_ID)) {
                //#endif
                    for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                        if (!player.getRecipeBook().contains(recipe.id())) {
                            player.awardRecipes(List.of(recipe));
                        }
                    }
                }
            }
        });
    }

    public static void onPlayerLoggedId(MinecraftServer server , ServerPlayer player) {
        if (server != null && server.isRunning() && hasActiveRecipeRule()) {
            Collection<RecipeHolder<?>> allRecipes = server.getRecipeManager().getRecipes();
            for (RecipeHolder<?> recipe : allRecipes) {
                //#if MC<12102
                if (recipe.id().getNamespace().equals(CarpetSDKAddition.MOD_ID) && !player.getRecipeBook().contains(recipe.id())) {
                //#elseif MC<260100
                //$$ if (recipe.id().location().getNamespace().equals(CarpetSDKAddition.MOD_ID) && !player.getRecipeBook().contains(recipe.id())) {
                //#else
                //$$ if (recipe.id().identifier().getNamespace().equals(CarpetSDKAddition.MOD_ID) && !player.getRecipeBook().contains(recipe.id())) {
                //#endif
                    player.awardRecipes(List.of(recipe));
                }
            }
        }
    }

    private static boolean hasActiveRecipeRule() {
        Field[] fields = CarpetSDKAdditionSettings.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(RecipeRule.class)) {
                try {
                    field.setAccessible(true);
                    if (field.getBoolean(null)) {
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    CarpetSDKAddition.LOGGER.warn("Failed to access RecipeRule field: {}", field.getName(), e);
                }
            }
        }
        return false;
    }
}