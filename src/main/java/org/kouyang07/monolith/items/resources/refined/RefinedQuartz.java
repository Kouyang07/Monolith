package org.kouyang07.monolith.items.resources.refined;

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
import org.kouyang07.monolith.items.resources.enchanted.EnchantedQuartz;

public class RefinedQuartz extends MonoItems {

  @Getter private static final RefinedQuartz instance = new RefinedQuartz();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.QUARTZ_BRICKS, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Refined Quartz").color(Monolith.PURPLE));
      item.setItemMeta(meta);
      meta.addEnchant(Enchantment.DURABILITY, 1, true);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Extremely dense quartz").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Resource").color(GRAY));
    item.lore(lore);

    items.put("refined_quartz", this);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "refined_quartz");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("DDD", "DDD", "DDD");
    recipe.setIngredient('D', EnchantedQuartz.getItem());
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }
}
