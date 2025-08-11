package dev.alphacerium.advancedgroups.core;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import dev.alphacerium.advancedgroups.AdvancedGroupCommandsVoicechatPlugin;
import dev.alphacerium.advancedgroups.exception.ReleaseGroupException;
import net.minecraft.server.level.ServerPlayer;

public class ReleaseGroup {
    public static void releaseGroup(ServerPlayer player) throws ReleaseGroupException {
        if (AdvancedGroupCommandsVoicechatPlugin.SERVER_API == null) {
            throw new ReleaseGroupException("Voice chat not connected");
        }

        VoicechatConnection playerConnection = AdvancedGroupCommandsVoicechatPlugin.SERVER_API.getConnectionOf(player.getUUID());
        if (playerConnection == null) {
            throw new ReleaseGroupException("Player is not connected to voice chat");
        }

        playerConnection.setGroup(null);
    }
}
