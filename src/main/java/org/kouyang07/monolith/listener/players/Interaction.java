package org.kouyang07.monolith.listener.players;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.cooldown.CoolDownItems;
import org.kouyang07.monolith.items.cooldown.Cooldown;
import org.kouyang07.monolith.items.MonoItemsIO;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import static org.kouyang07.monolith.Monolith.debug;

public class Interaction implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        if(event.getAction().isRightClick()){
            onIngotOfGambling(event);
            onBloodSacrifice(event);
            onDeathCount(event);
        }
    }
    private void onDeathCount(PlayerInteractEvent event) {
        ItemStack deathCount = null;
        // Check both hands for the Ingot of Gambling
        for (ItemStack item : new ItemStack[]{event.getPlayer().getInventory().getItemInOffHand(), event.getPlayer().getInventory().getItemInMainHand()}) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && MonoItems.deathCount.create().getItemMeta().equals(meta)) {
                deathCount = item;
                break;
            }
        }
        if (deathCount != null) {
            if (event.getPlayer().getHealth() > 17) {
                event.getPlayer().setHealth(event.getPlayer().getHealth() - 17);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 19));
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 9));
                getPlugin(Monolith.class).getServer().getScheduler().runTaskLater(getPlugin(Monolith.class), () -> {
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 19));
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 1));
                }, 400L); // 60 ticks = 3 seconds
                event.getPlayer().sendMessage(Component.text("The Death Count has granted you power").color(Monolith.SUCCESS_COLOR_GREEN));
            } else {
                event.getPlayer().sendMessage(Component.text("You do not have enough health to use this item").color(Monolith.SUCCESS_COLOR_RED));
            }
        }
    }

    private void onBloodSacrifice(PlayerInteractEvent event) {
        ItemStack bloodSacrifice = null;
        // Check both hands for the Ingot of Gambling
        for (ItemStack item : new ItemStack[]{event.getPlayer().getInventory().getItemInOffHand(), event.getPlayer().getInventory().getItemInMainHand()}) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && MonoItems.bloodSacrifice.create().getItemMeta().equals(meta)) {
                bloodSacrifice = item;
                break;
            }
        }
        if(bloodSacrifice != null){
            if(event.getPlayer().getHealth() > 10){
                event.getPlayer().setHealth(event.getPlayer().getHealth() - 10);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
                event.getPlayer().sendMessage(Component.text("The Blood Sacrifice has granted you strength").color(Monolith.SUCCESS_COLOR_GREEN));
            }else{
                event.getPlayer().sendMessage(Component.text("You do not have enough health to use this item").color(Monolith.SUCCESS_COLOR_RED));
            }
        }
    }

    private void onIngotOfGambling(PlayerInteractEvent event) {
        ItemStack ingotOfGambling = null;
        // Check both hands for the Ingot of Gambling
        for (ItemStack item : new ItemStack[]{event.getPlayer().getInventory().getItemInOffHand(), event.getPlayer().getInventory().getItemInMainHand()}) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && MonoItems.ingotOfGambling.create().getItemMeta().equals(meta)) {
                ingotOfGambling = item;
                break;
            }
        }
        if (!useable(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling)) {
            event.getPlayer().sendMessage(Component.text("You may not use it yet, its on cooldown").color(Monolith.SUCCESS_COLOR_RED));
            return;
        }

        if (ingotOfGambling != null) {
            Random rand = new Random();
            int chance = rand.nextInt(100) + 1;

            if (chance <= 15) {
                // 15% chance to get Weakness 1
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 0));
                addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling, 90);
            } else if (chance <= 50) {
                // 35% chance to get Weakness 2
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 1));
                addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling, 90);
            } else if (chance <= 65) {
                // 15% chance to get Strength 1
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0));
                addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling, 90);
            } else {
                // 35% chance to get Strength 2
                addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling, 90);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 1));
            }
        }
    }

    private void addCoolDown(UUID uuid, CoolDownItems item, double coolDown){
        if(Monolith.cooldownList.containsKey(uuid)){
            boolean alreadySet = false;
            for(Cooldown cd : Monolith.cooldownList.get(uuid)){
                if(cd.getItem().equals(item)){
                    cd.setExpiration((long) (System.currentTimeMillis() + (coolDown * 1000)));
                    if(debug) {
                        getLogger().log(Level.INFO, "Overwrote " + Monolith.cooldownList.get(uuid).get(Monolith.cooldownList.get(uuid).size() - 1).getItem() + " expiring at " + Monolith.cooldownList.get(uuid).get(Monolith.cooldownList.get(uuid).size() - 1).getExpiration());
                    }
                    alreadySet = true;
                }
            }
            if(!alreadySet) {
                Monolith.cooldownList.get(uuid).add(new Cooldown(item, (long) (System.currentTimeMillis() + (coolDown * 1000))));
                if(debug) {
                    getLogger().log(Level.INFO, "Appended " + Monolith.cooldownList.get(uuid).get(Monolith.cooldownList.get(uuid).size() - 1).getItem() + " expiring at " + Monolith.cooldownList.get(uuid).get(Monolith.cooldownList.get(uuid).size() - 1).getExpiration());
                }
            }
        }else{
            Monolith.cooldownList.put(uuid, new ArrayList<>());
            Monolith.cooldownList.get(uuid).add(new Cooldown(item, (long) (System.currentTimeMillis() + (coolDown * 1000))));
            if(debug) {
                getLogger().log(Level.INFO, "Added " + Monolith.cooldownList.get(uuid).get(Monolith.cooldownList.get(uuid).size() - 1).getItem() + " expiring at " + Monolith.cooldownList.get(uuid).get(Monolith.cooldownList.get(uuid).size() - 1).getExpiration());
            }
        }
    }

    private boolean useable(UUID uuid, CoolDownItems item){
        if(debug) {
            getLogger().log(Level.INFO, "Looking for " + item + " by " + uuid);
        }
        if(Monolith.cooldownList.containsKey(uuid)) {
            for (Cooldown cd : Monolith.cooldownList.get(uuid)) {
                if (cd.getItem().equals(item) && cd.getExpiration() < System.currentTimeMillis()) {
                    getLogger().log(Level.INFO, "Ready to use, current time is " + System.currentTimeMillis() + " expired at " + cd.getExpiration() + ", " + Math.abs((System.currentTimeMillis() - cd.getExpiration()))/1000 + " ago");
                    return true;
                }else{
                    if(debug){
                        getLogger().log(Level.INFO, "On cooldown, current time is " + System.currentTimeMillis() + " expiring at " + cd.getExpiration() + " with " + (System.currentTimeMillis() - cd.getExpiration()) + " left");
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
