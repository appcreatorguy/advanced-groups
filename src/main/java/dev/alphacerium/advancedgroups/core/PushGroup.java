package dev.alphacerium.advancedgroups.core;

import de.maxhenkel.enhancedgroups.config.PersistentGroup;
import de.maxhenkel.voicechat.api.Group;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import dev.alphacerium.advancedgroups.AdvancedGroupCommands;
import dev.alphacerium.advancedgroups.AdvancedGroupCommandsVoicechatPlugin;
import dev.alphacerium.advancedgroups.exception.PushGroupException;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class PushGroup {
    public static void pushGroup(ServerPlayer player, UUID groupID) throws PushGroupException {
        if (AdvancedGroupCommandsVoicechatPlugin.SERVER_API == null) {
            throw new PushGroupException("Voice chat not connected");
        }

        PersistentGroup persistentGroup = AdvancedGroupCommands.PERSISTENT_GROUP_STORE.getGroup(groupID);
        if (persistentGroup == null) {
            throw new PushGroupException("Group not found or not persistent");
        }

        VoicechatConnection playerConnection = AdvancedGroupCommandsVoicechatPlugin.SERVER_API.getConnectionOf(player.getUUID());

        if (playerConnection == null) {
            throw new PushGroupException("Player is not connected to voice chat");
        }

        UUID voicechatID = AdvancedGroupCommands.PERSISTENT_GROUP_STORE.getVoicechatId(persistentGroup.getId());

        Group group = AdvancedGroupCommandsVoicechatPlugin.SERVER_API.getGroup(voicechatID);
        if (group == null) {
            throw new PushGroupException("Group not found or not persistent");
        }

        playerConnection.setGroup(group);
    }
}

