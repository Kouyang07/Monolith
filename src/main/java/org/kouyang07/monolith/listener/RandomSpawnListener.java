package org.kouyang07.monolith.listener;

import java.util.Objects;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class RandomSpawnListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    if (!event.getPlayer().hasPlayedBefore()) {
      randomSpawn(event.getPlayer());
    }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    if (Objects.equals(
        event.getPlayer().getRespawnLocation(), event.getPlayer().getWorld().getSpawnLocation())) {
      randomSpawn(event.getPlayer());
    }
  }

  private void randomSpawn(Player player) {
    Random random = new Random();
    World world = player.getWorld();
    Location randomLocation;

    do {
      int x = random.nextInt(10000) - 5000; // Generate X within -500 to 499
      int z = random.nextInt(10000) - 5000; // Generate Z within -500 to 499
      int y = world.getHighestBlockYAt(x, z) - 1; // Get the top non-air block's Y

      randomLocation = new Location(world, x, y, z);
    } while (!randomLocation.getBlock().getType().isSolid()
        || randomLocation.getBlock().isLiquid());

    // Teleport the player to one block above the found location to ensure they're not stuck
    player.teleport(randomLocation.add(0, 1, 0));
  }
}
