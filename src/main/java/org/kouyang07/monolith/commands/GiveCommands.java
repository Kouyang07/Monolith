package org.kouyang07.monolith.commands;

import static org.kouyang07.monolith.items.MonoItems.items;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.combat.weapons.*;

public class GiveCommands implements CommandExecutor, TabCompleter {

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

    ItemStack item;

    if (items.containsKey(args[0])) {
      item = items.get(args[0]).create();
    } else {
      sender.sendMessage(Component.text("Item not recognized").color(Monolith.FAIL_COLOR_RED));
      return true;
    }

    // If an item was created, give it to the player
    if (item != null) {
      player.getInventory().addItem(item);
      sender.sendMessage("Given " + args[0] + " to " + player.getName());
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String alias, String[] args) {
    if (args.length == 1) {
      return items.keySet().stream()
          .filter(name -> name.startsWith(args[0].toLowerCase()))
          .sorted()
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
