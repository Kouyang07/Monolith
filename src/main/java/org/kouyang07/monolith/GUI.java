package org.kouyang07.monolith;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
}
