package org.kouyang07.monolith.listener.players;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.MonoItemsIO;

import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.debug;

public class Move implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        double additionalSpeed = Monolith.playerAttributes.get(player.getUniqueId()).getExtraSpeed();
        // Increase speed attribute
        if (player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.1 + additionalSpeed/100); // Normal is 0.1
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        onSoldiersRepose(event);
    }

    private void onSoldiersRepose(PlayerToggleSneakEvent event){
        if(event.getPlayer().getInventory().getLeggings() != null) {
            if (event.getPlayer().getInventory().getLeggings().getItemMeta().equals(MonoItems.soldiersRepose.create().getItemMeta()) && !event.getPlayer().isSneaking()) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, false, false, true));
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false, true));
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 9, false, false, true));
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0, false, false, true));
            } else {
                event.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
                event.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                event.getPlayer().removePotionEffect(PotionEffectType.SLOW);
                event.getPlayer().removePotionEffect(PotionEffectType.WEAKNESS);
            }
        }
    }
}
