package dev.alphacerium.advancedgroups;

import de.maxhenkel.admiral.permissions.PermissionManager;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.util.TriState;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class AdvancedGroupPermissionManager implements PermissionManager<CommandSourceStack> {

    private final List<Permission> PERMISSIONS;

    public AdvancedGroupPermissionManager() {
        Permission PUSH_GROUP_PERMISSION = new Permission("advancedgroups.push_group", AdvancedGroupCommands.CONFIG.pushGroupPermissionType.get());
        Permission RELEASE_GROUP_PERMISSION = new Permission("advancedgroups.release_group", AdvancedGroupCommands.CONFIG.releaseGroupPermissionType.get());

        PERMISSIONS = List.of(PUSH_GROUP_PERMISSION, RELEASE_GROUP_PERMISSION);
    }

    @Override
    public boolean hasPermission(CommandSourceStack commandSourceStack, String permission) {
        for (Permission p : PERMISSIONS) {
            if (!p.permission.equals(permission)) {
                continue;
            }
            if (commandSourceStack.isPlayer()) {
                return p.hasPermission(commandSourceStack.getPlayer());
            }
            if (p.getType().equals(PermissionType.OPS)) {
                return commandSourceStack.hasPermission(2);
            } else {
                return p.hasPermission(null);
            }
        }
        return false;
    }

    private static Boolean loaded;

    private static boolean isFabricPermissionsAPILoaded() {
        if (loaded == null) {
            loaded = FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0");
            if (loaded) {
                AdvancedGroupCommands.LOGGER.info("Fabric permissions API loaded");
            }
        }
        return loaded;
    }

    private record Permission(String permission, PermissionType type) {

        public boolean hasPermission(@Nullable ServerPlayer player) {
            if (isFabricPermissionsAPILoaded()) {
                return checkFabricPermission(player);
            }
            return type.hasPermission(player);
        }

        private boolean checkFabricPermission(@Nullable ServerPlayer player) {
            if (player == null) {
                return false;
            }
            TriState permissionValue = Permissions.getPermissionValue(player, permission);
            return switch (permissionValue) {
                case DEFAULT -> type.hasPermission(player);
                case TRUE -> true;
                default -> false;
            };
        }

        public PermissionType getType() {
            return type;
        }
    }

    public enum PermissionType {
        EVERYONE, NOONE, OPS;

        boolean hasPermission(@Nullable ServerPlayer player) {
            return switch (this) {
                case EVERYONE -> true;
                case NOONE -> false;
                case OPS -> player != null && player.hasPermissions(Objects.requireNonNull(player.getServer()).getOperatorUserPermissionLevel());
            };
        }
    }
}
