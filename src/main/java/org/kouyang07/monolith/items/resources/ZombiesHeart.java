package org.kouyang07.monolith.items.resources;

import static org.kouyang07.monolith.Monolith.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.items.MonoItems;

public class ZombiesHeart extends MonoItems {
  @Getter private static final ZombiesHeart instance = new ZombiesHeart();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.ZOMBIE_HEAD, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Zombie's Heart").color(PURPLE));
      item.setItemMeta(meta);
      meta.addEnchant(Enchantment.DURABILITY, 1, false);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Heart of the zombie... gross...").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Resource").color(GRAY));
    item.lore(lore);

    items.put("zombies_heart", this);
    return item;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }
}
