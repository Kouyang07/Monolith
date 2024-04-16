package org.kouyang07.monolith.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kouyang07.monolith.items.combat.armors.GolemChestplate;
import org.kouyang07.monolith.items.combat.armors.RageHelmet;
import org.kouyang07.monolith.items.combat.armors.SoldiersRepose;
import org.kouyang07.monolith.items.combat.armors.SpeedBoots;
import org.kouyang07.monolith.items.combat.misc.TotemOfSafekeeping;
import org.kouyang07.monolith.items.combat.spells.BloodSacrifice;
import org.kouyang07.monolith.items.combat.spells.DeathCount;
import org.kouyang07.monolith.items.combat.weapons.*;

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
                item = TotemOfSafekeeping.getItem();
                break;
            case "ingot_of_gambling":
                item = IngotOfGambling.getItem();
                break;
            case "golem_chestplate":
                item = GolemChestplate.getItem();
                break;
            case "speed_boots":
                item = SpeedBoots.getItem();
                break;
            case "sword_of_greed":
                item = SwordOfGreed.getItem();
                break;
            case "rage_helmet":
                item = RageHelmet.getItem();
                break;
            case "claymore":
                item = Claymore.getItem();
                break;
            case "blood_sacrifice":
                item = BloodSacrifice.getItem();
                break;
            case "death_count":
                item = DeathCount.getItem();
                break;
            case "soldiers_repose":
                item = SoldiersRepose.getItem();
                break;
            case "sonic_crossbow":
                item = SonicCrossbow.getItem();
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
