package org.kouyang07.monolith.listener.players;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.MonoItemsIO;

import java.util.logging.Level;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import static org.kouyang07.monolith.Monolith.debug;
import static org.bukkit.Bukkit.getLogger;

public class Projectile implements Listener {
    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        sonicCrossbow(event);
    }

    private void sonicCrossbow(ProjectileLaunchEvent event) {
        ProjectileSource shooter = event.getEntity().getShooter();
        if (shooter instanceof Player player) {
            if(debug){
                getLogger().log(Level.INFO, "Projectile event triggered by " + player.getName());
            }
            if (player.getInventory().getItemInMainHand().getItemMeta().equals(MonoItems.sonicCrossbow.create().getItemMeta())) {
                event.setCancelled(true);
                shootBeam(player);
            }
        }
    }

    private void shootBeam(Player player) {
        Location eye = player.getEyeLocation();
        Vector direction = eye.getDirection();

        for (int i = 0; i < 30; i++) { // Length of the beam
            Location point = eye.add(direction);
            player.getWorld().spawnParticle(Particle.END_ROD, point, 1, 0, 0, 0, 0.01);

            // Check for and damage all Damageable entities except the shooter
            point.getNearbyEntities(0.5, 0.5, 0.5).stream()
                    .filter(entity -> entity instanceof Damageable && entity != player)
                    .forEach(entity -> ((Damageable) entity).damage(16));
        }
    }
}
