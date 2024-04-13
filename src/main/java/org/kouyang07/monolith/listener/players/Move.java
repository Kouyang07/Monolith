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
        Player player = event.getPlayer();
        boolean hasLeggings = player.getInventory().getLeggings() != null;
        boolean correctLeggings = hasLeggings && player.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS
                && MonoItemsIO.equals(player.getInventory().getLeggings().getItemMeta(), MonoItems.soldiersRepose.create().getItemMeta());
        boolean shouldApplyEffects = correctLeggings && !player.isSneaking();

        if (shouldApplyEffects) {
            applyReposeEffects(player);
        } else {
            removeReposeEffects(player);
        }
    }

    private void applyReposeEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 9, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0, false, false, true));
    }

    private void removeReposeEffects(Player player) {
        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.WEAKNESS);
    }
}
