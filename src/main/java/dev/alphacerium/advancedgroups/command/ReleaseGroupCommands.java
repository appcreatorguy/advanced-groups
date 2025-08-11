package dev.alphacerium.advancedgroups.command;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.admiral.annotations.RequiresPermission;
import dev.alphacerium.advancedgroups.core.ReleaseGroup;
import dev.alphacerium.advancedgroups.exception.ReleaseGroupException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

@RequiresPermission("advancedgroups.release_group")
@Command(PushGroupCommands.PUSH_GROUP_COMMAND)
public class ReleaseGroupCommands {
    @Command("release")
    public int release(CommandContext<CommandSourceStack> context, @Name("player") ServerPlayer player) {
        try {
            ReleaseGroup.releaseGroup(player);
        } catch (ReleaseGroupException e) {
            context.getSource().sendFailure(Component.literal(e.getMessage()));
            return 0;
        }
        context.getSource().sendSuccess(() -> Component.literal("%s successfully released from group".formatted(player.getName().getString())), false);
        return 1;
    }
}
