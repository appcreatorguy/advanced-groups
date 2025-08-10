package dev.alphacerium.advancedgroups.command;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.admiral.annotations.RequiresPermission;
import de.maxhenkel.enhancedgroups.config.PersistentGroup;
import dev.alphacerium.advancedgroups.AdvancedGroupCommands;
import dev.alphacerium.advancedgroups.core.PushGroup;
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
        return PushGroup.pushGroup(context, player, persistentGroup.getId());
    }

    @Command("push")
    public int push(CommandContext<CommandSourceStack> context, @Name("player") ServerPlayer player, @Name("id") UUID groupID) {
        return PushGroup.pushGroup(context, player, groupID);
    }

}
