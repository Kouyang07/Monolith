package org.kouyang07.monolith.listener.players;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.GUI;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.MonoItemsIO;
import org.kouyang07.monolith.items.combat.armors.GolemChestplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPluginsFolder;
import static org.kouyang07.monolith.Monolith.debug;
import static org.kouyang07.monolith.Monolith.playerAttributes;
import static org.kouyang07.monolith.items.MonoItems.golemChestplate;

public class Inventory implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        skillTree(event);
    }

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        armorChange(event);
    }

    @EventHandler
    public void onHold(PlayerItemHeldEvent event) {
        sonicCrossbow(event);
    }

    private void skillTree(InventoryClickEvent event){
        if (event.getInventory() == GUI.skillTree){
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || clickedItem.getItemMeta() == null) return;
            CustomAttributes playerAttributes;
            if(Monolith.playerAttributes.containsKey(player.getUniqueId())){
                playerAttributes = Monolith.playerAttributes.get(player.getUniqueId());
            }else{
                playerAttributes = new CustomAttributes(0, 0, 0);
            }

            switch (clickedItem.getType()) {
                case IRON_SWORD:
                    if (pay(player, playerAttributes.getExtraDamage())) {
                        playerAttributes.setExtraDamage(playerAttributes.getExtraDamage() + 1);
                        Monolith.playerAttributes.put(player.getUniqueId(), playerAttributes);
                        player.sendMessage(Component.text(playerAttributes.getExtraDamage() - 1 + "->" + playerAttributes.getExtraDamage()).color(Monolith.SUCCESS_COLOR_GREEN));
                        if (debug) {
                            getLogger().log(Level.INFO, "Player " + player.getName() + " has increased their damage by 1");
                        }
                        saveAttributes();
                    }
                    break;
                case IRON_CHESTPLATE:
                    if (pay(player, playerAttributes.getExtraDefense())) {
                        playerAttributes.setExtraDefense(playerAttributes.getExtraDefense() + 1);
                        Monolith.playerAttributes.put(player.getUniqueId(), playerAttributes);
                        player.sendMessage(Component.text(playerAttributes.getExtraDefense() - 1 + "->" + playerAttributes.getExtraDefense()).color(Monolith.SUCCESS_COLOR_GREEN));
                        if (debug) {
                            getLogger().log(Level.INFO, "Player " + player.getName() + " has increased their defense by 1");
                        }
                    }
                    break;
                case FEATHER:
                    if (pay(player, playerAttributes.getExtraSpeed())) {
                        playerAttributes.setExtraSpeed(playerAttributes.getExtraSpeed() + 1);
                        Monolith.playerAttributes.put(player.getUniqueId(), playerAttributes);
                        player.sendMessage(Component.text(playerAttributes.getExtraSpeed() - 1 + "->" + playerAttributes.getExtraSpeed()).color(Monolith.SUCCESS_COLOR_GREEN));
                        if (debug) {
                            getLogger().log(Level.INFO, "Player " + player.getName() + " has increased their speed by 1");
                        }
                    }
                    break;
            }
        }
    }
    private boolean pay(Player player, int currentValue){
        if(player.getGameMode().equals(GameMode.CREATIVE)){
            return true;
        }
        switch(currentValue){
            case 0:
                if(player.getLevel() > 10){
                    player.setLevel(player.getLevel() - 10);
                    return true;
                }else{
                    player.sendMessage(Component.text("You do not have enough levels to purchase this upgrade! Needs 10 and you have " + player.getLevel()).color(Monolith.SUCCESS_COLOR_RED));
                    return false;
                }
            case 1:
                if(player.getLevel() > 25){
                    player.setLevel(player.getLevel() - 25);
                    return true;
                }else{
                    player.sendMessage(Component.text("You do not have enough levels to purchase this upgrade! Needs 25 and you have " + player.getLevel()).color(Monolith.SUCCESS_COLOR_RED));
                    return false;
                }
            case 2:
                if(player.getLevel() > 50){
                    player.setLevel(player.getLevel() - 50);
                    return true;
                }else{
                    player.sendMessage(Component.text("You do not have enough levels to purchase this upgrade! Needs 50 and you have " + player.getLevel()).color(Monolith.SUCCESS_COLOR_RED));
                    return false;
                }
            case 3:
                if(player.getLevel() > 100){
                    player.setLevel(player.getLevel() - 100);
                    return true;
                }else{
                    player.sendMessage(Component.text("You do not have enough levels to purchase this upgrade! Needs 100 and you have " + player.getLevel()).color(Monolith.SUCCESS_COLOR_RED));
                    return false;
                }
            default:
                    player.sendMessage(Component.text("You've reached the limit").color(Monolith.SUCCESS_COLOR_GREEN));
                return false;
        }
    }
    public void saveAttributes() {
        // Correct the file path
        File dataFile = new File(getPluginsFolder(), "Monolith/playerAttributes.txt");

        // Prepare the data to be written
        StringBuilder data = new StringBuilder();
        playerAttributes.forEach((uuid, customAttributes) -> data.append(uuid)
                .append(":").append(customAttributes.getExtraDamage()).append(",")
                .append(customAttributes.getExtraDefense()).append(",")
                .append(customAttributes.getExtraSpeed()).append("\n"));

        try {
            // Ensure the parent directory exists
            File parentDir = dataFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs(); // Creates the directory if it does not exist
            }

            // Create the file if it does not exist
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }

            // Write data to file
            FileWriter writer = new FileWriter(dataFile);
            writer.write(data.toString());
            writer.close();

            // Debug log
            if(debug) {
                getLogger().log(Level.INFO, "Saved the players attributes to " + dataFile.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void armorChange(PlayerArmorChangeEvent event){
        if(event.getSlotType() == PlayerArmorChangeEvent.SlotType.CHEST) {
            if (event.getNewItem().getType() == Material.IRON_CHESTPLATE) {
                if (event.getNewItem().getItemMeta().equals(golemChestplate.create().getItemMeta())) {
                    applyGolemEffects(event.getPlayer());
                }
            } else {
                removeGolemEffects(event.getPlayer());
            }
        }else if(event.getSlotType() == PlayerArmorChangeEvent.SlotType.FEET){
            if(event.getNewItem().getType() == Material.LEATHER_BOOTS && event.getNewItem().getItemMeta().equals(MonoItems.speedBoots.create().getItemMeta())){
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false, true));
            }else{
                event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            }
        }
    }

    private void applyGolemEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0, false, false, true));
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(1.0);
    }

    private void removeGolemEffects(Player player) {
        player.removePotionEffect(PotionEffectType.SLOW);
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(0);
    }

    private void sonicCrossbow(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        ItemStack sonicCrossBow = null;
        for (ItemStack item : new ItemStack[]{player.getInventory().getItemInOffHand(), player.getInventory().getItemInMainHand()}) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && MonoItems.sonicCrossbow.create().getItemMeta().equals(meta)) {
                sonicCrossBow = item;
                break;
            }
        }
        if(sonicCrossBow == null) return;
        if (sonicCrossBow.equals(MonoItems.sonicCrossbow.create().getItemMeta())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 0, true, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, -1, 19, true, true));

        } else{
                player.removePotionEffect(PotionEffectType.SPEED);
                player.removePotionEffect(PotionEffectType.WEAKNESS);}
        }
    }
