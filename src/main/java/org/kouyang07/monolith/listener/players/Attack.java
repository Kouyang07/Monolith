package org.kouyang07.monolith.listener.players;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.MonoItemsIO;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import static org.kouyang07.monolith.Monolith.debug;

public class Attack implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        statDamage(event);
        weaponCheck(event);
        armorCheck(event);
        showDamage(event);
    }

    private void statDamage(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player) {
            double originalDamage = event.getDamage();
            double additionDamage = Monolith.playerAttributes.get(event.getDamager().getUniqueId()).getExtraDamage();
            event.setDamage(originalDamage + additionDamage);
        } else if (event.getEntity() instanceof Player) {
            double originalDamage = event.getDamage();
            double defense = Monolith.playerAttributes.get(event.getEntity().getUniqueId()).getExtraDefense();
            event.setDamage(originalDamage - defense * 5);
        }
    }
    private void weaponCheck(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player player && event.getEntity() instanceof LivingEntity) {
            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            // Assuming MonoItems.swordOfGreed.create() returns a static final reference to the item.
            ItemStack swordOfGreed = MonoItems.swordOfGreed.create();

            // Check if the main hand item is a golden sword.
            if (mainHandItem.getType() == Material.GOLDEN_SWORD) {
                // Check for matching display name and lore.
                if (mainHandItem.hasItemMeta() && swordOfGreed.hasItemMeta()) {
                    ItemMeta mainHandMeta = mainHandItem.getItemMeta();
                    ItemMeta greedMeta = swordOfGreed.getItemMeta();

                    String mainHandName = mainHandMeta.getDisplayName();
                    String greedName = greedMeta.getDisplayName();
                    List<String> mainHandLore = mainHandMeta.getLore();
                    List<String> greedLore = greedMeta.getLore();

                    if (mainHandName.equals(greedName) && Objects.equals(mainHandLore, greedLore)) {
                        if(debug){
                            getLogger().log(Level.INFO, "Sword of Greed found in " + player.getName() + "'s inventory.");
                        }
                        if(player.getInventory().contains(Material.DIAMOND, 1)){
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND, 1));
                            player.sendMessage(Component.text("You have paid the price for your greed.").color(Monolith.SUCCESS_COLOR_GREEN));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, false, false, true));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, false, false, true));
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(Component.text("You do not have enough diamonds to pay the price.").color(Monolith.SUCCESS_COLOR_RED));
                        }
                    } else {
                        // Not the Sword of Greed.
                        if(debug){
                            getLogger().log(Level.INFO, "No Sword of Greed found in " + player.getName() + "'s inventory.");
                        }
                    }
                }
            }
        }
    }
    private void armorCheck(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player player){
            if(player.getInventory().getHelmet() == null){
                return;
            }
            ItemStack helmet = player.getInventory().getHelmet();
            ItemStack rageHelmet = MonoItems.rageHelmet.create();
            if(MonoItemsIO.equals(helmet.getItemMeta(), rageHelmet.getItemMeta())){
                if(debug){
                    getLogger().log(Level.INFO, "Rage Helmet found on " + player.getName() + ".");
                }
                double health = player.getHealth();
                double damage = event.getDamage();
                double newDamage = damage + (Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() - health) * 0.5;
                event.setDamage(newDamage);
            } else {
                // Not the Rage Helmet.
                if(debug){
                    getLogger().log(Level.INFO, "No Rage Helmet found on " + player.getName() + ".");
                }
            }
        }
    }
    private void showDamage(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player) {
            double damage = event.getFinalDamage();

            // Spawn the armor stand at the location where the entity was hit
            Location hitLocation = event.getEntity().getLocation();
            hitLocation.add(2, 0, 0); // Raise the height to make the armor stand more visible

            ArmorStand armorStand = (ArmorStand) hitLocation.getWorld().spawnEntity(hitLocation, EntityType.ARMOR_STAND);
            armorStand.setGravity(false);
            armorStand.setCanPickupItems(false);
            armorStand.customName(Component.text(Math.round(damage)).color(
                    damage > 10 ? TextColor.color(139, 0, 0) : // Red if damage is greater than 5
                            damage > 5 ? TextColor.color(255, 140, 0) : // Yellow if damage is greater than 2
                                    TextColor.color(128, 128, 128) // Green otherwise
            ));
            armorStand.setCustomNameVisible(true);
            armorStand.setVisible(false);

            // Make the armor stand disappear after some time
            getPlugin(Monolith.class).getServer().getScheduler().runTaskLater(getPlugin(Monolith.class), armorStand::remove, 60L); // 60 ticks = 3 seconds
        }
    }
}
