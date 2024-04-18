package org.kouyang07.monolith.items.combat.weapons;

import static org.kouyang07.monolith.Monolith.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItemsIO;

public class Claymore extends MonoItemsIO implements Listener {
  @Getter private static final Claymore instance = new Claymore();
  @Getter private static final ItemStack item = instance.create();
  @Getter static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.STONE_SWORD, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Claymore").color(PURPLE));
      AttributeModifier damageModifier =
          new AttributeModifier(
              UUID.randomUUID(),
              "generic.attackDamage",
              15.0,
              AttributeModifier.Operation.ADD_NUMBER,
              EquipmentSlot.HAND);
      AttributeModifier modifier =
          new AttributeModifier(
              UUID.randomUUID(),
              "generic.attackSpeed",
              -3.0,
              AttributeModifier.Operation.ADD_NUMBER,
              EquipmentSlot.HAND);
      meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
      meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier);
      meta.addEnchant(Enchantment.DURABILITY, 10, true);
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Heavy but packs quite the power!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("0.5 swing speed").color(GRAY));
    item.lore(lore);
    return item;
  }

  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "claymore");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("OSO", "OSO", "ONO");
    recipe.setIngredient('O', Material.OBSIDIAN);
    recipe.setIngredient('S', Material.DIAMOND_SWORD);
    recipe.setIngredient('N', Material.NETHERITE_INGOT);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }
}
