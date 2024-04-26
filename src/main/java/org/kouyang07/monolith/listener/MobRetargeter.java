package org.kouyang07.monolith.listener;

import java.util.Objects;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class MobRetargeter implements Listener {
  @EventHandler
  public void onEntityTarget(EntityTargetEvent event) {
    if (event.getEntity().customName() != null
        && Objects.requireNonNull(event.getEntity().customName())
            .contains(Component.text("[Lvl "))) {
      if (!(event.getTarget() instanceof Player)
          || ((Player) event.getTarget()).getGameMode() == GameMode.CREATIVE) {
        event.setCancelled(true);
      }
    }
  }
}
