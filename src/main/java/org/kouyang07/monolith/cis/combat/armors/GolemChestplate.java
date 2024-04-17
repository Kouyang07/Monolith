package org.kouyang07.monolith.cis.combat.armors;

import static org.kouyang07.monolith.Monolith.*;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.cis.MonoItemsIO;

public class GolemChestplate extends MonoItemsIO implements Listener {
  @Getter private static final GolemChestplate instance = new GolemChestplate();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Golem Chestplate").color(PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Makes you immovable like a golem!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Gives slowness 1").color(GRAY));
    item.lore(lore);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "golem_chestplate");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("IGI", "III", "III");
    recipe.setIngredient('I', Material.IRON_BLOCK);
    recipe.setIngredient('G', Material.GOLDEN_APPLE);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onPlayerArmorChangeEvent(PlayerArmorChangeEvent event) {
    if (event.getSlotType() == PlayerArmorChangeEvent.SlotType.CHEST) {
      if (isItem(event.getNewItem(), item)) {
        applyGolemEffects(event.getPlayer());
      } else {
        removeGolemEffects(event.getPlayer());
      }
    }
  }

  private void applyGolemEffects(Player player) {
    player.addPotionEffect(
        new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0, false, false, true));
    Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE))
        .setBaseValue(1.0);
  }

  private void removeGolemEffects(Player player) {
    player.removePotionEffect(PotionEffectType.SLOW);
    Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE))
        .setBaseValue(0);
  }
}
