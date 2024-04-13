package org.kouyang07.monolith.listener.players;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.debug;

public class Attack implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        statDamage(event);
        weaponCheck(event);

    }

    private void statDamage(EntityDamageByEntityEvent event){
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
}
