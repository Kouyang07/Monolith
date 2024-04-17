package org.kouyang07.monolith.cis.combat.armors;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import static org.kouyang07.monolith.Monolith.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.cis.MonoItemsIO;

public class RageHelmet extends MonoItemsIO implements Listener {
  @Getter private static final RageHelmet instance = new RageHelmet();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.NETHERITE_HELMET, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Rage Helmet").color(PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("No pain no gain!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Gives + 0.4 damage per heart lost").color(GRAY));
    item.lore(lore);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "rage_helmet");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("N N", "BHB", "BBB");
    recipe.setIngredient('N', Material.NETHERITE_INGOT);
    recipe.setIngredient('B', Material.BLAZE_ROD);
    recipe.setIngredient('H', Material.NETHERITE_HELMET);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player player) {
      if (player.getInventory().getHelmet() == null) {
        return;
      }
      ItemStack helmet = player.getInventory().getHelmet();
      if (isItem(helmet, item)) {
        if (debug) {
          getLogger().log(Level.INFO, "Rage Helmet found on " + player.getName() + ".");
        }
        double health = player.getHealth();
        double damage = event.getDamage();
        double newDamage =
            damage
                + (Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH))
                            .getValue()
                        - health)
                    * 0.2;
        event.setDamage(newDamage);
      } else {
        // Not the Rage Helmet.
        if (debug) {
          getLogger().log(Level.INFO, "No Rage Helmet found on " + player.getName() + ".");
        }
      }
    }
  }
}
