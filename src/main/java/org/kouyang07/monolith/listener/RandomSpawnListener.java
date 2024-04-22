package org.kouyang07.monolith.listener;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RandomSpawnListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    if (!event.getPlayer().hasPlayedBefore()) {
      Random random = new Random();
      World world = event.getPlayer().getWorld();
      Location randomLocation;

      do {
        int x = random.nextInt(1000) - 500; // Generate X within -500 to 499
        int z = random.nextInt(1000) - 500; // Generate Z within -500 to 499
        int y = world.getHighestBlockYAt(x, z) - 1; // Get the top non-air block's Y

        randomLocation = new Location(world, x, y, z);
      } while (!randomLocation.getBlock().getType().isSolid()
          || randomLocation.getBlock().isLiquid());

      // Teleport the player to one block above the found location to ensure they're not stuck
      event.getPlayer().teleport(randomLocation.add(0, 1, 0));
    }
  }
}
