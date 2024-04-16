package org.kouyang07.monolith.items;

import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.kouyang07.monolith.GUI;
import org.kouyang07.monolith.Monolith;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPluginsFolder;
import static org.kouyang07.monolith.Monolith.debug;
import static org.kouyang07.monolith.Monolith.playerAttributes;

@Data
public class CustomAttributes implements Listener {

    int extraDamage;
    int extraDefense;
    int extraSpeed;
    public CustomAttributes(int extraDamage, int extraDefense, int extraSpeed) {
        this.extraDamage = extraDamage;
        this.extraDefense = extraDefense;
        this.extraSpeed = extraSpeed;
    }

    public CustomAttributes(){}

    @EventHandler
    private void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            double originalDamage = event.getDamage();
            double additionDamage = Monolith.playerAttributes.get(event.getDamager().getUniqueId()).getExtraDamage();
            event.setDamage(originalDamage + additionDamage);
        } else if (event.getEntity() instanceof Player) {
            double originalDamage = event.getDamage();
            double defense = Monolith.playerAttributes.get(event.getEntity().getUniqueId()).getExtraDefense();
            event.setDamage(originalDamage - (defense * 5));
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event){ //TODO: Need optimizations
        Player player = event.getPlayer();
        double additionalSpeed = Monolith.playerAttributes.get(player.getUniqueId()).getExtraSpeed();
        // Increase speed attribute
        if (player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.1 + additionalSpeed/100); // Normal is 0.1
        }
    }

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        if(!Monolith.playerAttributes.containsKey(event.getPlayer().getUniqueId())){
            Monolith.playerAttributes.put(event.getPlayer().getUniqueId(), new CustomAttributes(0, 0, 0));
        }
    }

    @EventHandler
    private void onInventoryClickEvent(InventoryClickEvent event){
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
                    player.sendMessage(Component.text("You do not have enough levels to purchase this upgrade! Needs 10 and you have " + player.getLevel()).color(Monolith.FAIL_COLOR_RED));
                    return false;
                }
            case 1:
                if(player.getLevel() > 25){
                    player.setLevel(player.getLevel() - 25);
                    return true;
                }else{
                    player.sendMessage(Component.text("You do not have enough levels to purchase this upgrade! Needs 25 and you have " + player.getLevel()).color(Monolith.FAIL_COLOR_RED));
                    return false;
                }
            case 2:
                if(player.getLevel() > 50){
                    player.setLevel(player.getLevel() - 50);
                    return true;
                }else{
                    player.sendMessage(Component.text("You do not have enough levels to purchase this upgrade! Needs 50 and you have " + player.getLevel()).color(Monolith.FAIL_COLOR_RED));
                    return false;
                }
            case 3:
                if(player.getLevel() > 100){
                    player.setLevel(player.getLevel() - 100);
                    return true;
                }else{
                    player.sendMessage(Component.text("You do not have enough levels to purchase this upgrade! Needs 100 and you have " + player.getLevel()).color(Monolith.FAIL_COLOR_RED));
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
}
