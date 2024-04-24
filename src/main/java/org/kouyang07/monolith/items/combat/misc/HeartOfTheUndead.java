package org.kouyang07.monolith.items.combat.misc;

import static org.kouyang07.monolith.Monolith.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.combat.weapons.IngotOfGambling;
import org.kouyang07.monolith.items.resources.drops.ZombiesHeart;

public class HeartOfTheUndead extends MonoItems implements Listener {
  @Getter private static final HeartOfTheUndead instance = new HeartOfTheUndead();
  @Getter private static final ItemStack item = instance.create();
  @Getter static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Heart of The Undead").color(PURPLE));

      // meta.un(Enchantment.DURABILITY, 1, false);
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Regains 2 permanent heart ").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Right click to use").color(GRAY));
    item.lore(lore);

    items.put("heart_of_the_undead", this);
    return item;
  }

  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "heart_of_the_undead");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape(" H ", "HGH", " H ");
    recipe.setIngredient('H', ZombiesHeart.getItem());
    recipe.setIngredient('G', Material.GOLDEN_APPLE);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onPlayerInteractEvent(PlayerInteractEvent event) {
    if (!event.getAction().isRightClick()) return;
    ItemStack heartOfTheUndead = null;
    // Check both hands for the Ingot of Gambling
    for (ItemStack item :
        new ItemStack[] {
          event.getPlayer().getInventory().getItemInOffHand(),
          event.getPlayer().getInventory().getItemInMainHand()
        }) {
      ItemMeta meta = item.getItemMeta();
      if (meta != null && IngotOfGambling.isItem(HeartOfTheUndead.getItem(), item)) {
        heartOfTheUndead = item;
        break;
      }
    }
    if (heartOfTheUndead == null) return;
    event.setCancelled(true);
    event
        .getPlayer()
        .sendMessage(
            Component.text("The Heart of the Undead has granted you 2 hearts")
                .color(SUCCESS_COLOR_GREEN));
    event.getPlayer().getInventory().remove(heartOfTheUndead);
    double maxHealth =
        Objects.requireNonNull(event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH))
                .getBaseValue()
            + 4;
    Objects.requireNonNull(event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH))
        .setBaseValue(maxHealth);
    event.getPlayer().setHealth(maxHealth);
  }
}
