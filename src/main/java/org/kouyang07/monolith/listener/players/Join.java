package org.kouyang07.monolith.listener.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kouyang07.monolith.Monolith;

public class Join implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(!Monolith.playerAttributes.containsKey(event.getPlayer().getUniqueId())){
            Monolith.playerAttributes.put(event.getPlayer().getUniqueId(), new CustomAttributes(0, 0, 0));
        }
    }
}
