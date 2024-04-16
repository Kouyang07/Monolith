package org.kouyang07.monolith.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kouyang07.monolith.GUI;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.CustomAttributes;

public class StatCommands implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if(strings.length > 0){
                if(strings[0].equalsIgnoreCase("reset")){
                    if(player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Component.text("Stats reset.").color(Monolith.SUCCESS_COLOR_GREEN));
                        if (Monolith.playerAttributes.containsKey(player.getUniqueId())) {
                            Monolith.playerAttributes.put(player.getUniqueId(), new CustomAttributes(0, 0, 0));
                        }
                        return true;
                    }
                }else{
                    player.sendMessage(Component.text("You must be in creative to reset").color(Monolith.FAIL_COLOR_RED));
                }
            }else{
                player.sendMessage(Component.text("Opening skill tree...").color(Monolith.SUCCESS_COLOR_GREEN));
                player.openInventory(GUI.skillTree);
            }
            return true;
        } else {
            commandSender.sendMessage("This command can only be run by a player.");
            return true;
        }
    }
}
