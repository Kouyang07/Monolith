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
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.resources.CompactNetherite;
import org.kouyang07.monolith.items.resources.CompactObsidian;

public class Claymore extends MonoItems implements Listener {
  @Getter private static final Claymore instance = new Claymore();
  @Getter private static final ItemStack item = instance.create();
  @Getter static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.NETHERITE_SWORD, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Claymore").color(PURPLE));
      AttributeModifier damageModifier =
          new AttributeModifier(
              UUID.randomUUID(),
              "generic.attackDamage",
              14.0,
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

    items.put("claymore", this);
    return item;
  }

  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "claymore");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("OOO", "OEO", "NSN");
    recipe.setIngredient('O', CompactObsidian.getItem());
    recipe.setIngredient('E', Material.DIAMOND_SWORD);
    recipe.setIngredient('N', CompactNetherite.getItem());
    recipe.setIngredient('S', Material.STICK);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }
}
