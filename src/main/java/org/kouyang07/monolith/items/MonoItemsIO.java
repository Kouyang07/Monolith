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
}
