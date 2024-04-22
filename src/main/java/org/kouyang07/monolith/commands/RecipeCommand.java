package org.kouyang07.monolith.commands;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import static org.kouyang07.monolith.items.MonoItems.items;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.kouyang07.monolith.GUI;
import org.kouyang07.monolith.Monolith;

public class RecipeCommand implements CommandExecutor, Listener, TabCompleter {
  @Override
  public boolean onCommand(
      @NotNull CommandSender commandSender,
      @NotNull Command command,
      @NotNull String s,
      @NotNull String[] strings) {
    if (strings.length != 1) {
      commandSender.sendMessage("Usage: /monorecipe [item]");
      return true;
    }

    if (!(commandSender instanceof Player player)) {
      commandSender.sendMessage("Only players can use this command.");
      return true;
    }

    Recipe recipe =
        Bukkit.getRecipe(new NamespacedKey(getPlugin(Monolith.class), strings[0].toLowerCase()));

    if (recipe == null) {
      player.sendMessage("Item not found.");
      return true;
    }

    player.openInventory(GUI.showRecipeGUI(recipe));
    return true;
  }

  @EventHandler
  private void onInventoryClickEvent(InventoryClickEvent event) {
    if (event.getView().title().equals(Component.text("Monolith Custom Recipe"))) {
      event.setCancelled(true);
    }
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
