package org.kouyang07.monolith.items.combat.armors;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;

public class SoldiersRepose extends MonoItems implements Listener {
  @Getter private static final SoldiersRepose instance = new SoldiersRepose();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Solder's Repose").color(PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Stand strong!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Gives the following effect while sneaking").color(GRAY));
    lore.add(Component.text("Resistance 1").color(GRAY));
    lore.add(Component.text("Regeneration 1").color(GRAY));
    lore.add(Component.text("Slowness 10").color(GRAY));
    lore.add(Component.text("Weakness 3").color(GRAY));
    item.lore(lore);

    items.put("soldiers_repose", this);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "soldiers_repose");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("BDB", "DGD", "B B");
    recipe.setIngredient('D', Material.DIAMOND);
    recipe.setIngredient('B', Material.DIAMOND_BLOCK);
    recipe.setIngredient('G', Material.GOLDEN_APPLE);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
    if (event.getPlayer().getInventory().getLeggings() != null) {
      if (isItem(event.getPlayer().getInventory().getLeggings(), SoldiersRepose.getItem())
          && !event.getPlayer().isSneaking()) {

        if (debug) {
          getLogger().log(Level.INFO, event.getPlayer().getName() + " equipped Soldier's Repose");
        }

        event
            .getPlayer()
            .addPotionEffect(
                new PotionEffect(
                    PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, false, false, true));
        event
            .getPlayer()
            .addPotionEffect(
                new PotionEffect(
                    PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false, true));
        event
            .getPlayer()
            .addPotionEffect(
                new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 9, false, false, true));
        event
            .getPlayer()
            .addPotionEffect(
                new PotionEffect(
                    PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 2, false, false, true));
      } else {
        event.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
        event.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        event.getPlayer().removePotionEffect(PotionEffectType.SLOW);
        event.getPlayer().removePotionEffect(PotionEffectType.WEAKNESS);
      }
    }
  }
}
