package org.kouyang07.monolith.items.combat.misc;

import static org.kouyang07.monolith.Monolith.*;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;

public class TotemOfSafekeeping extends MonoItems implements Listener {
  @Getter private static final TotemOfSafekeeping instance = new TotemOfSafekeeping();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.AMETHYST_SHARD, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Totem of Safekeeping").color(PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Keeps your inventory on death").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Consumes on death").color(GRAY));
    lore.add(Component.text("Only if on main or off-hand").color(GRAY));
    item.lore(lore);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack totemOfSafekeeping = create();
    NamespacedKey totemOfSafeKeepingKey =
        new NamespacedKey(getPlugin(Monolith.class), "totem_of_safekeeping");
    ShapedRecipe recipe = new ShapedRecipe(totemOfSafeKeepingKey, totemOfSafekeeping);
    recipe.shape(" N ", "NTN", " N ");
    recipe.setIngredient('N', Material.NETHER_STAR);
    recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);

    items.put("totem_of_safekeeping", this);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onPlayerDeathEvent(PlayerDeathEvent event) {
    ItemStack totemItem = null;
    // Check both hands for the Totem of Safekeeping
    for (ItemStack item :
        new ItemStack[] {
          event.getEntity().getInventory().getItemInOffHand(),
          event.getEntity().getInventory().getItemInMainHand()
        }) {
      if (isItem(item, TotemOfSafekeeping.getItem())) {
        totemItem = item;
        break;
      }
    }

    // If the Totem of Safekeeping was found, set keep inventory and remove the totem
    if (totemItem != null) {
      event.setKeepInventory(true);
      event.getDrops().clear(); // Optionally clear drops to prevent duplication

      // This line ensures the totem is removed after the event
      totemItem.setAmount(totemItem.getAmount() - 1);
      event
          .getEntity()
          .sendMessage(
              Component.text("Your Totem of Safekeeping has protected your inventory!")
                  .color(Monolith.SUCCESS_COLOR_GREEN));
    }
  }
}
