package org.kouyang07.monolith.commands;

import static org.kouyang07.monolith.mobs.MonoMobs.mobs;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.mobs.Elite_Skeleton;
import org.kouyang07.monolith.mobs.Elite_Zombie;

public class SpawnCommands implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player player) {
      if(!player.isOp()) return true;
      if (command.getName().equalsIgnoreCase("monospawn")) {
        switch (args[0].toLowerCase()) {
          case "elite_zombie":
            Elite_Zombie.getInstance().spawn(player.getLocation());
            break;
          case "elite_skeleton":
            Elite_Skeleton.getInstance().spawn(player.getLocation());
            break;
          default:
            sender.sendMessage(Component.text("Invalid mob type").color(Monolith.FAIL_COLOR_RED));
            break;
        }
        return true;
      }
    } else {
      sender.sendMessage(
          Component.text("Only players can use this command").color(Monolith.FAIL_COLOR_RED));
    }
    return false;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String alias, String[] args) {
    if (args.length == 1) {
      return mobs.keySet().stream()
          .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
          .sorted()
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
