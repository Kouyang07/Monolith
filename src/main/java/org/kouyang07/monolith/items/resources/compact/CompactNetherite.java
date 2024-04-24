package org.kouyang07.monolith.items.resources.compact;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import static org.kouyang07.monolith.Monolith.GOLD;
import static org.kouyang07.monolith.Monolith.GRAY;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;

public class CompactNetherite extends MonoItems {

  @Getter private static final CompactNetherite instance = new CompactNetherite();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.NETHERITE_INGOT, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Compact Netherite").color(Monolith.PURPLE));
      item.setItemMeta(meta);
      meta.addEnchant(Enchantment.DURABILITY, 1, true);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Very dense netherite").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Resource").color(GRAY));
    item.lore(lore);

    items.put("compact_netherite", this);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "compact_netherite");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("NNN", "NNN", "NNN");
    recipe.setIngredient('N', Material.NETHERITE_INGOT);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }
}
