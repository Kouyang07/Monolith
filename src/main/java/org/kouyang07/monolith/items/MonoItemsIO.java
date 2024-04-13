package org.kouyang07.monolith.items;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
public abstract class MonoItemsIO {

    public ItemStack create() {
        return null;
    }

    public Recipe recipe() {
        return null;
    }

    public static boolean equals(ItemMeta item1, ItemMeta item2){
        return item1.equals(item2);
    }
}
