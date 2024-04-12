package org.kouyang07.monolith.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Combat {
    public static ItemStack createTotemOfSafekeeping() {
        ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Totem of Safekeeping");
            // Optionally add some lore or custom tags as needed
            item.setItemMeta(meta);
        }
        return item;
    }


}
