package dev.alphacerium.advancedgroups.core;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.enhancedgroups.config.PersistentGroup;
import de.maxhenkel.voicechat.api.Group;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import dev.alphacerium.advancedgroups.AdvancedGroupCommands;
import dev.alphacerium.advancedgroups.AdvancedGroupCommandsVoicechatPlugin;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class PushGroup {
    public static int pushGroup(CommandContext<CommandSourceStack> context, ServerPlayer player, UUID groupId) {
        if (AdvancedGroupCommandsVoicechatPlugin.SERVER_API == null) {
            context.getSource().sendFailure(Component.literal("Voice chat not connected"));
            return 0;
        }

        PersistentGroup persistentGroup = AdvancedGroupCommands.PERSISTENT_GROUP_STORE.getGroup(groupId);
        if (persistentGroup == null) {
            context.getSource().sendFailure(Component.literal("Group not found or not persistent"));
            return 0;
        }

        VoicechatConnection playerConnection = AdvancedGroupCommandsVoicechatPlugin.SERVER_API.getConnectionOf(player.getUUID());

        if (playerConnection == null) {
            context.getSource().sendFailure(Component.literal("Player is not connected to voice chat"));
            return 0;
        }

        UUID voicechatID = AdvancedGroupCommands.PERSISTENT_GROUP_STORE.getVoicechatId(persistentGroup.getId());

        Group group = AdvancedGroupCommandsVoicechatPlugin.SERVER_API.getGroup(voicechatID);
        if (group == null) {
            context.getSource().sendFailure(Component.literal("Group not found or not persistent"));
            return 0;
        }

        playerConnection.setGroup(group);
        context.getSource().sendSuccess(() -> Component.literal("Player successfully joined %s group".formatted(group.getName())), false);
        return 1;
    }
}
