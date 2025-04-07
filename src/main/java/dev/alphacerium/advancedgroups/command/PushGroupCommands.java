package dev.alphacerium.advancedgroups.command;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.admiral.annotations.RequiresPermission;
import de.maxhenkel.enhancedgroups.config.PersistentGroup;
import de.maxhenkel.voicechat.api.Group;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import dev.alphacerium.advancedgroups.AdvancedGroupCommands;
import dev.alphacerium.advancedgroups.AdvancedGroupCommandsVoicechatPlugin;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

@RequiresPermission("advancedgroups.push_group")
@Command(PushGroupCommands.PUSH_GROUP_COMMAND)
public class PushGroupCommands {

    public static final String PUSH_GROUP_COMMAND = "advancedgroups";

    @Command("push")
    public int push(CommandContext<CommandSourceStack> context, @Name("player") ServerPlayer player, @Name("group_name") String groupName) {
        PersistentGroup persistentGroup = AdvancedGroupCommands.PERSISTENT_GROUP_STORE.getGroup(groupName);
        if (persistentGroup == null) {
            context.getSource().sendFailure(Component.literal("Group not found or not persistent"));
            return 0;
        }
        return pushGroup(context, player, persistentGroup.getId());
    }

    @Command("push")
    public int push(CommandContext<CommandSourceStack> context, @Name("player") ServerPlayer player, @Name("id") UUID groupID) {
        return pushGroup(context, player, groupID);
    }

    public int pushGroup(CommandContext<CommandSourceStack> context, ServerPlayer player, UUID groupId) {
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
