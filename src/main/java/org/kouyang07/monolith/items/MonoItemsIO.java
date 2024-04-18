package org.kouyang07.monolith.items;

import java.util.Objects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class MonoItemsIO {

  public ItemStack create() {
    return null;
  }

  public Recipe recipe() {
    return null;
  }

  public static void register() {}

  public static boolean isItem(ItemStack item1, ItemStack item2) {
    if (item1 == null || item2 == null) return false;
    if (item1.getItemMeta() == null || item2.getItemMeta() == null) return false;
    if (item1.getItemMeta().lore() == null || item2.getItemMeta().lore() == null) return false;
    return Objects.equals(item1.getItemMeta().lore(), item2.getItemMeta().lore());
  }
}
