package org.kouyang07.monolith.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kouyang07.monolith.items.MonoItems;

public class GiveCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure the command is being sent by a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        // Ensure the command has at least one argument (the item name)
        if (args.length < 1) {
            sender.sendMessage("Please specify an item name.");
            return true;
        }

        ItemStack item = null;

        // Determine which item to give based on the first argument
        switch (args[0].toLowerCase()) {
            case "totem_of_safekeeping":
                item = MonoItems.totemOfSafekeeping.create();
                break;
            case "ingot_of_gambling":
                item = MonoItems.ingotOfGambling.create();
                break;
            default:
                sender.sendMessage("Item not recognized.");
                break;
        }

        // If an item was created, give it to the player
        if (item != null) {
            player.getInventory().addItem(item);
            sender.sendMessage("Given " + args[0] + " to " + player.getName());
        }

        return true;
    }
}
