package org.kouyang07.monolith.items.combat.spells;

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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;

public class BloodSacrifice extends MonoItems implements Listener {
  @Getter private static final BloodSacrifice instance = new BloodSacrifice();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Blood Sacrifice").color(Monolith.PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Sacrifice your blood for power!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Sacrifice 5 hearts for a strength 3 buff for 4 seconds").color(GRAY));
    item.lore(lore);

    items.put("blood_sacrifice", this);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "blood_sacrifice");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("SBS", "BEB", "SBS");
    recipe.setIngredient('S', Material.SOUL_SAND);
    recipe.setIngredient('B', Material.BLAZE_ROD);
    recipe.setIngredient('E', Material.BOOK);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onPlayerInteractEvent(PlayerInteractEvent event) {
    ItemStack bloodSacrifice = null;
    // Check both hands for the Ingot of Gambling
    for (ItemStack item :
        new ItemStack[] {
          event.getPlayer().getInventory().getItemInOffHand(),
          event.getPlayer().getInventory().getItemInMainHand()
        }) {
      ItemMeta meta = item.getItemMeta();
      if (meta != null && isItem(item, BloodSacrifice.item)) {
        bloodSacrifice = item;
        break;
      }
    }
    if (bloodSacrifice != null) {
      if (event.getPlayer().getHealth() > 10) {
        event.getPlayer().setHealth(event.getPlayer().getHealth() - 10);
        event
            .getPlayer()
            .addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
        event
            .getPlayer()
            .sendMessage(
                Component.text("The Blood Sacrifice has granted you strength")
                    .color(Monolith.SUCCESS_COLOR_GREEN));
      } else {
        event
            .getPlayer()
            .sendMessage(
                Component.text("You do not have enough health to use this item")
                    .color(Monolith.FAIL_COLOR_RED));
      }
    }
  }
}
