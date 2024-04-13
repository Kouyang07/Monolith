package org.kouyang07.monolith.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kouyang07.monolith.GUI;

public class StatCommands implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            player.openInventory(GUI.skillTree);
            return true;
        } else {
            commandSender.sendMessage("This command can only be run by a player.");
            return true;
        }
    }
}
