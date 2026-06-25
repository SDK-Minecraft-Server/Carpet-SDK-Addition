package com.renzaifei.carpetsdkaddition.api.recipe;

import com.renzaifei.carpetsdkaddition.CarpetSDKAddition;
import com.renzaifei.carpetsdkaddition.api.recipe.builder.RecipeBuilder;
import com.renzaifei.carpetsdkaddition.rules.CustomRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RecipeRuleHelper {

    private RecipeRuleHelper() {}

    public static void onValueChange(MinecraftServer server) {
        if (server == null || !server.isRunning()) {
            CarpetSDKAddition.LOGGER.warn("server is null or not running, skipping reload");
            return;
        }

        RecipeManager.clearRecipeListMemory(RecipeBuilder.getInstance());
        CustomRecipes.getInstance().buildRecipes();
        server.execute(() -> {
            server.reloadResources(Collections.emptyList());
            unlockRecipes(server);
        });
    }

    private static void unlockRecipes(MinecraftServer server) {
        net.minecraft.world.item.crafting.RecipeManager vanillaRM = server.getRecipeManager();
        Collection<RecipeHolder<?>> holders = vanillaRM.getRecipes();

        List<RecipeHolder<?>> ourRecipes = new ArrayList<>();
        for (RecipeHolder<?> holder : holders) {
            //#if MC>=12102
            //$$ ResourceLocation id = holder.id().location();
            //#else
            ResourceLocation id = holder.id();
            //#endif
            if (id.getNamespace().equals(CarpetSDKAddition.MOD_ID)) {
                ourRecipes.add(holder);
            }
        }

        if (ourRecipes.isEmpty()) {
            return;
        }

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            player.awardRecipes(ourRecipes);
        }
    }
}