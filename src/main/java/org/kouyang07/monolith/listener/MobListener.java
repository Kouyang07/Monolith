package org.kouyang07.monolith.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class MobListener implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntity() instanceof Zombie) {
            if (!(event.getTarget() instanceof Player)) {
                // If the target is not a player, cancel the targeting
                event.setCancelled(true);
            }
        }
    }
}
