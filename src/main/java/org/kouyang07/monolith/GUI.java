package org.kouyang07.monolith;

import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI {
  public static Inventory skillTree = skillTreeGUI();

  private static Inventory skillTreeGUI() {
    Inventory gui = Bukkit.createInventory(null, 9, "Upgrade Skills");

    ItemStack damage = new ItemStack(Material.IRON_SWORD);
    ItemMeta damageMeta = damage.getItemMeta();
    damageMeta.setDisplayName(ChatColor.RED + "+1 Damage");
    damage.setItemMeta(damageMeta);
    gui.setItem(3, damage);

    ItemStack defense = new ItemStack(Material.IRON_CHESTPLATE);
    ItemMeta defenseMeta = defense.getItemMeta();
    defenseMeta.setDisplayName(ChatColor.BLUE + "+1 Defense");
    defense.setItemMeta(defenseMeta);
    gui.setItem(4, defense);

    ItemStack speed = new ItemStack(Material.FEATHER);
    ItemMeta speedMeta = speed.getItemMeta();
    speedMeta.setDisplayName(ChatColor.GREEN + "+1 Speed");
    speed.setItemMeta(speedMeta);
    gui.setItem(5, speed);
    return gui;
  }

  public static Inventory showRecipeGUI(Recipe recipe) {
    Inventory gui = Bukkit.createInventory(null, 27, "Monolith Custom Recipe");
    // Placeholder panes for empty slots to enhance visibility
    ItemStack placeholderPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    ItemMeta placeholderMeta = placeholderPane.getItemMeta();
    if (placeholderMeta != null) {
      placeholderMeta.displayName(Component.text(" ")); // Set to space to have no name
      placeholderPane.setItemMeta(placeholderMeta);
    }

    // Fill all slots with placeholder panes to create a visible grid
    for (int i = 0; i < gui.getSize(); i++) {
      gui.setItem(i, placeholderPane);
    }

    // Centered grid slot positions
    int[] gridSlots = {1, 2, 3, 10, 11, 12, 19, 20, 21};

    if (recipe instanceof ShapedRecipe) {
      ShapedRecipe shaped = (ShapedRecipe) recipe;
      String[] shape = shaped.getShape();
      Map<Character, ItemStack> ingredientMap = shaped.getIngredientMap();

      // Fill the GUI based on the shaped recipe's shape and ingredients
      for (int i = 0; i < shape.length; i++) {
        for (int j = 0; j < shape[i].length(); j++) {
          char ingredientChar = shape[i].charAt(j);
          ItemStack item = ingredientMap.get(ingredientChar);
          if (item != null) {
            gui.setItem(gridSlots[i * 3 + j], item.clone());
          } else {
            gui.setItem(gridSlots[i * 3 + j], new ItemStack(Material.AIR));
          }
        }
      }
    } else if (recipe instanceof ShapelessRecipe) {
      ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
      List<ItemStack> ingredients = shapeless.getIngredientList();

      // Fill the GUI by placing shapeless recipe ingredients left to right, top to bottom
      for (int i = 0; i < ingredients.size(); i++) {
        ItemStack item = ingredients.get(i);
        if (item != null && i < gridSlots.length) {
          gui.setItem(gridSlots[i], item.clone());
        }
      }
    }

    // Place the result item to the right of the grid (slot 16 if we are using 27-slot inventory)
    gui.setItem(16, recipe.getResult());
    gui.setItem(14, new ItemStack(Material.CRAFTING_TABLE));

    // Clear placeholders where ingredients were placed
    for (int slot : gridSlots) {
      if (gui.getItem(slot) == null) {
        gui.clear(slot);
      }
    }

    // Open the GUI for the player
    return gui;
  }
}
