package org.kouyang07.monolith.items;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public abstract class MonoItemsIO {

    public ItemStack create() {
        return null;
    }

    public Recipe recipe() {
        return null;
    }

    public static boolean equals(ItemMeta item1, ItemMeta item2){
        if(item1 == null || item2 == null){
            return false;
        }
        if(item1.displayName() != null && item2.displayName() != null){
            if(!Objects.equals(item1.displayName(), item2.displayName())){
                return false;
            }
        }
        if(item1.lore() != null && item2.lore() != null){
            return Objects.equals(item1.lore(), item2.lore());
        }
        return true;
    }
}
