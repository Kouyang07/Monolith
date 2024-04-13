package org.kouyang07.monolith.listener.players;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.kouyang07.monolith.Monolith;

import java.util.Objects;

public class Move implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        double additionalSpeed = Monolith.playerAttributes.get(event.getPlayer().getUniqueId()).getExtraSpeed();
        Player player = event.getPlayer();
        // Increase speed attribute
        if (player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.1 + additionalSpeed/100); // Normal is 0.1
        }
    }
}
