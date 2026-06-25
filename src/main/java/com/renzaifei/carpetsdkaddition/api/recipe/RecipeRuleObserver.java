package com.renzaifei.carpetsdkaddition.api.recipe;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import com.renzaifei.carpetsdkaddition.CarpetSDKAddition;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RecipeRuleObserver extends Validator<Boolean> {

    @Override
    public Boolean validate(@Nullable CommandSourceStack commandSourceStack, CarpetRule<Boolean> carpetRule, Boolean aBoolean, String s) {
        if (!Objects.equals(carpetRule.value(), aBoolean)) {
            if (commandSourceStack != null) {
                MinecraftServer server = commandSourceStack.getServer();
                RecipeRuleHelper.onValueChange(server);
            } else {
                CarpetSDKAddition.LOGGER.warn("CommandSourceStack is null, cannot reload recipes");
            }
        }
        return aBoolean;
    }
}
