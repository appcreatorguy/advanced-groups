package dev.alphacerium.advancedgroups.core;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import dev.alphacerium.advancedgroups.AdvancedGroupCommandsVoicechatPlugin;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ReleaseGroup {
    public static int releaseGroup(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        if (AdvancedGroupCommandsVoicechatPlugin.SERVER_API == null) {
            context.getSource().sendFailure(Component.literal("Voice chat not connected"));
            return 0;
        }

        VoicechatConnection playerConnection = AdvancedGroupCommandsVoicechatPlugin.SERVER_API.getConnectionOf(player.getUUID());
        if (playerConnection == null) {
            context.getSource().sendFailure(Component.literal("Player is not connected to voice chat"));
            return 0;
        }

        playerConnection.setGroup(null);
        context.getSource().sendSuccess(() -> Component.literal("%s successfully released from group".formatted(player.getName().getString())), false);
        return 1;
    }
}
