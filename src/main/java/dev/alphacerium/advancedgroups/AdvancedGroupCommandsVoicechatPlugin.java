package dev.alphacerium.advancedgroups;

import de.maxhenkel.enhancedgroups.EnhancedGroups;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;

public class AdvancedGroupCommandsVoicechatPlugin implements VoicechatPlugin {

    public static VoicechatServerApi SERVER_API;

    @Override
    public String getPluginId() {
        return AdvancedGroupCommands.MOD_ID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, event -> {
            SERVER_API = event.getVoicechat();
            AdvancedGroupCommands.PERSISTENT_GROUP_STORE = EnhancedGroups.PERSISTENT_GROUP_STORE;
        });
    }
}
