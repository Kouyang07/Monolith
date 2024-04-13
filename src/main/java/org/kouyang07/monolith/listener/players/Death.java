package org.kouyang07.monolith.listener.players;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.MonoItemsIO;
import org.kouyang07.monolith.items.combat.TotemOfSafekeeping;

public class Death implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        onPlayerDeathLS(event);
        onPlayerDeathTotem(event);
    }
    private void onPlayerDeathLS(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        Player killer = deceased.getKiller();

        // Decrease max health of the deceased player by 2
        double newMaxHealth = Math.max(2, deceased.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2); // Ensuring not to go below 2
        deceased.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);

        // If killed by another player, increase the killer's max health by 2
        if (killer != null) {
            double killerNewMaxHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2;
            killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killerNewMaxHealth);
        }
    }

    private void onPlayerDeathTotem(PlayerDeathEvent event){
        ItemStack totemItem = null;
        // Check both hands for the Totem of Safekeeping
        for (ItemStack item : new ItemStack[]{event.getEntity().getInventory().getItemInOffHand(), event.getEntity().getInventory().getItemInMainHand()}) {
            ItemMeta meta = item.getItemMeta();
            if (MonoItemsIO.equals(MonoItems.totemOfSafekeeping.create().getItemMeta(), meta)) {
                totemItem = item;
                break;
            }
        }

        // If the Totem of Safekeeping was found, set keep inventory and remove the totem
        if (totemItem != null) {
            event.setKeepInventory(true);
            event.getDrops().clear(); // Optionally clear drops to prevent duplication

            // This line ensures the totem is removed after the event
            totemItem.setAmount(totemItem.getAmount() - 1);
            event.getEntity().sendMessage(Component.text("Your Totem of Safekeeping has protected your inventory!").color(Monolith.SUCCESS_COLOR_GREEN));
        }
    }
}
