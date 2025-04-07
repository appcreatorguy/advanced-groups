package dev.alphacerium.advancedgroups.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;
import dev.alphacerium.advancedgroups.AdvancedGroupPermissionManager;

public class CommonConfig {
    public final ConfigEntry<AdvancedGroupPermissionManager.PermissionType> pushGroupPermissionType;
    public final ConfigEntry<AdvancedGroupPermissionManager.PermissionType> releaseGroupPermissionType;

    public CommonConfig(ConfigBuilder builder) {
        pushGroupPermissionType = builder
                .enumEntry("push_group_permission_level", AdvancedGroupPermissionManager.PermissionType.OPS)
                .comment(
                        "The default permission level of the /advancedgroups push command",
                        "EVERYONE - Every player can use this command",
                        "OPS - Operators can use this command",
                        "NOONE - This command can't be used by anyone"
                );
        releaseGroupPermissionType = builder
                .enumEntry("release_group_permission_level", AdvancedGroupPermissionManager.PermissionType.OPS)
                .comment(
                        "The default permission level of the /advancedgroups release command",
                        "EVERYONE - Every player can use this command",
                        "OPS - Operators can use this command",
                        "NOONE - This command can't be used by anyone"
                );
    }
}
