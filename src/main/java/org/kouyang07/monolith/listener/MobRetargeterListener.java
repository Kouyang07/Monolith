package org.kouyang07.monolith.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class MobRetargeterListener implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntity() instanceof Mob) {
            if (!(event.getTarget() instanceof Player) || ((Player) event.getTarget()).getGameMode() == GameMode.CREATIVE){
                event.setCancelled(true);
            }
        }
    }
}
