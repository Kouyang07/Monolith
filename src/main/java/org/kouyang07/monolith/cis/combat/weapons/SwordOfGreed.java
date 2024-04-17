package org.kouyang07.monolith.cis.combat.weapons;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.cis.MonoItemsIO;

public class SwordOfGreed extends MonoItemsIO implements Listener {
  @Getter private static final SwordOfGreed instance = new SwordOfGreed();
  @Getter private static final ItemStack item = instance.create();
  @Getter static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Sword of Greed").color(PURPLE));
      AttributeModifier damageModifier =
          new AttributeModifier(
              UUID.randomUUID(),
              "generic.attackDamage",
              20.0,
              AttributeModifier.Operation.ADD_NUMBER,
              EquipmentSlot.HAND);
      AttributeModifier speedModifier =
          new AttributeModifier(
              UUID.randomUUID(),
              "generic.attackSpeed",
              -0.6,
              AttributeModifier.Operation.ADD_NUMBER,
              EquipmentSlot.HAND);

      meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
      meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifier);

      meta.addEnchant(Enchantment.DURABILITY, 5, false);

      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Only the rich of the rich can wield this sword!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Costs 1 diamond per hit").color(GRAY));
    item.lore(lore);
    return item;
  }

  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "sword_of_greed");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("DDD", "DSG", "GGG");
    recipe.setIngredient('G', Material.GOLD_BLOCK);
    recipe.setIngredient('D', Material.DIAMOND_BLOCK);
    recipe.setIngredient('S', Material.DIAMOND_SWORD);
    getLogger().log(Level.INFO, "Sword of Greed recipe added");
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player player && event.getEntity() instanceof LivingEntity) {
      ItemStack mainHandItem = player.getInventory().getItemInMainHand();
      ItemStack swordOfGreed = SwordOfGreed.getItem();
      if (isItem(mainHandItem, swordOfGreed)) {
        if (debug) {
          getLogger()
              .log(Level.INFO, "Sword of Greed found in " + player.getName() + "'s inventory.");
        }
        if (player.getInventory().contains(Material.DIAMOND, 1)) {
          player.getInventory().removeItem(new ItemStack(Material.DIAMOND, 1));
          player.sendMessage(
              Component.text("You have paid the price for your greed.")
                  .color(Monolith.SUCCESS_COLOR_GREEN));
          player.addPotionEffect(
              new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, false, false, true));
          player.addPotionEffect(
              new PotionEffect(PotionEffectType.SPEED, 100, 0, false, false, true));
        } else {
          event.setCancelled(true);
          player.sendMessage(
              Component.text("You do not have enough diamonds to pay the price.")
                  .color(Monolith.FAIL_COLOR_RED));
        }
      } else {
        // Not the Sword of Greed.
        if (debug) {
          getLogger()
              .log(Level.INFO, "No Sword of Greed found in " + player.getName() + "'s inventory.");
        }
      }
    }
  }
}
