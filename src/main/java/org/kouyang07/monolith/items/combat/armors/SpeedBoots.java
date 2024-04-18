package org.kouyang07.monolith.items.combat.armors;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.*;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItemsIO;

public class SpeedBoots extends MonoItemsIO implements Listener {
  @Getter private static final SpeedBoots instance = new SpeedBoots();
  @Getter private static final ItemStack item = instance.create();
  @Getter static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Speed Boots").color(PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Zoooom!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Gives speed 1").color(GRAY));
    item.lore(lore);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "speed_boots");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("S S", "L L", "L L");
    recipe.setIngredient('S', Material.SUGAR);
    recipe.setIngredient('L', Material.LEATHER);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onArmorChange(PlayerArmorChangeEvent event) {
    if (event.getSlotType() == PlayerArmorChangeEvent.SlotType.FEET) {
      if (isItem(event.getNewItem(), SpeedBoots.getItem())) {
        getLogger().log(Level.INFO, event.getPlayer().getName() + " equipped Speed Boots");
        event
            .getPlayer()
            .addPotionEffect(
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false, true));
      } else {
        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
      }
    }
  }
}
