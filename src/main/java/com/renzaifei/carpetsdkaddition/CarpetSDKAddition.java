package com.renzaifei.carpetsdkaddition;

import carpet.CarpetServer;
import com.renzaifei.carpetsdkaddition.rules.dispenserCollectCauldron;
import com.renzaifei.carpetsdkaddition.rules.dispenserCollectXp;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CarpetSDKAddition implements ModInitializer{
	public static final String MOD_ID = "carpet-sdk-addition";
	public static final String MOD_NAME = "Carpet SDK Addition";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static String version;

	@Override
	public void onInitialize() {
		version = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
		CarpetServer.manageExtension(new CarpetSDKAdditionExtension());
		dispenserCollectXp.init();
		dispenserCollectCauldron.init();
	}
}