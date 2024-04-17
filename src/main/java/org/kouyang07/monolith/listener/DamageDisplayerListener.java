package org.kouyang07.monolith.listener;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.kouyang07.monolith.Monolith;

public class DamageDisplayerListener implements Listener {
  @EventHandler
  public void onEntityDamageByEntityEvent(EntityDamageEvent event) {
    if (event.getCause().equals(EntityDamageEvent.DamageCause.CUSTOM)
        || event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
      double damage = event.getFinalDamage();

      // Spawn the armor stand at the location where the entity was hit
      Location hitLocation = event.getEntity().getLocation();
      hitLocation.add(2, 0, 0); // Raise the height to make the armor stand more visible

      ArmorStand armorStand =
          (ArmorStand) hitLocation.getWorld().spawnEntity(hitLocation, EntityType.ARMOR_STAND);
      armorStand.setGravity(false);
      armorStand.setCanPickupItems(false);
      armorStand.customName(
          Component.text(Math.round(damage))
              .color(
                  damage > 10
                      ? TextColor.color(139, 0, 0)
                      : // Red if damage is greater than 5
                      damage > 5
                          ? TextColor.color(255, 140, 0)
                          : // Yellow if damage is greater than 2
                          TextColor.color(128, 128, 128) // Green otherwise
                  ));
      armorStand.setCustomNameVisible(true);
      armorStand.setVisible(false);

      // Make the armor stand disappear after some time
      getPlugin(Monolith.class)
          .getServer()
          .getScheduler()
          .runTaskLater(getPlugin(Monolith.class), armorStand::remove, 60L); // 60 ticks = 3 seconds
    }
  }
}
