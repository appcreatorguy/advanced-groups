package dev.alphacerium.advancedgroups;

import de.maxhenkel.admiral.MinecraftAdmiral;
import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.enhancedgroups.config.PersistentGroupStore;
import dev.alphacerium.advancedgroups.command.PushGroupCommands;
import dev.alphacerium.advancedgroups.command.ReleaseGroupCommands;
import dev.alphacerium.advancedgroups.config.CommonConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AdvancedGroupCommands implements ModInitializer {
	public static final String MOD_ID = "advancedgroups";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static CommonConfig CONFIG;
	public static PersistentGroupStore PERSISTENT_GROUP_STORE;

    public static AdvancedGroupPermissionManager PERMISSION_MANAGER;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		Path configFolder = Paths.get(".", "config").resolve(MOD_ID);
		CONFIG = ConfigBuilder.builder(CommonConfig::new).path(configFolder.resolve("%s.properties".formatted(MOD_ID))).build();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> MinecraftAdmiral.builder(dispatcher, registryAccess)
				.addCommandClasses(
						PushGroupCommands.class,
						ReleaseGroupCommands.class
				)
				.setPermissionManager(PERMISSION_MANAGER)
				.build());
	}

}