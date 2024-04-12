package org.kouyang07.monolith.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LSCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ls") && sender instanceof Player) {
            Player player = (Player) sender;

            // Check if the player has permission (is op)
            if (!player.isOp()) {
                player.sendMessage("You don't have permission to use this command.");
                return true;
            }

            // Check if the command has the correct number of arguments
            if (args.length != 3) {
                player.sendMessage("Usage: /ls set [player] [maxHealth]");
                return true;
            }

            // Get the target player
            Player target = player.getServer().getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("Player not found.");
                return true;
            }

            // Parse max health value
            double maxHealth;
            try {
                maxHealth = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid health value.");
                return true;
            }

            // Check if the maxHealth is within a reasonable range
            if (maxHealth < 1 || maxHealth > 2048) { // You can adjust the max value as per your server rules
                player.sendMessage("Health value out of range. Must be between 1 and 2048.");
                return true;
            }

            // Set the player's max health
            target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            target.setHealth(maxHealth); // Optionally set current health to max as well
            player.sendMessage("Set " + target.getName() + "'s max health to " + maxHealth);
            return true;
        }
        return false;
    }
}
