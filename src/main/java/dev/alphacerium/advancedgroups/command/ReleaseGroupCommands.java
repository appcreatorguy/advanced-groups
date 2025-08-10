package dev.alphacerium.advancedgroups.command;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.admiral.annotations.RequiresPermission;
import dev.alphacerium.advancedgroups.core.ReleaseGroup;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

@RequiresPermission("advancedgroups.release_group")
@Command(PushGroupCommands.PUSH_GROUP_COMMAND)
public class ReleaseGroupCommands {
    @Command("release")
    public int release(CommandContext<CommandSourceStack> context, @Name("player") ServerPlayer player) {
        return ReleaseGroup.releaseGroup(context, player);
    }
}
