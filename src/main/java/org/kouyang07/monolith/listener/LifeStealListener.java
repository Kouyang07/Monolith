package org.kouyang07.monolith.listener;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class LifeStealListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        onPlayerDeathLS(event);
    }
    private void onPlayerDeathLS(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        Player killer = deceased.getKiller();

        // Decrease max health of the deceased player by 2
        double newMaxHealth = Math.max(2, deceased.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2); // Ensuring not to go below 2
        deceased.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);

        // If killed by another player, increase the killer's max health by 2
        if (killer != null) {
            double killerNewMaxHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2;
            killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killerNewMaxHealth);
        }
    }
}
