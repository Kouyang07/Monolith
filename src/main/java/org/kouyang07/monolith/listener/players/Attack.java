package org.kouyang07.monolith.listener.players;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.kouyang07.monolith.Monolith;

public class Attack implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            double originalDamage = event.getDamage();
            double additionDamage = Monolith.playerAttributes.get(event.getDamager().getUniqueId()).getExtraDamage();
            event.setDamage(originalDamage + additionDamage);
        } else if (event.getEntity() instanceof Player && event.getDamager() instanceof LivingEntity) {
            double originalDamage = event.getDamage();
            double defense = Monolith.playerAttributes.get(event.getEntity().getUniqueId()).getExtraDefense();
            event.setDamage(originalDamage - defense);
        }
    }
}
