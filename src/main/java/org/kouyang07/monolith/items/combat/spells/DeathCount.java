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

@Getter
public class DeathCount extends MonoItems implements Listener {
  @Getter private static final DeathCount instance = new DeathCount();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Death Count").color(Monolith.PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Sacrifice your blood for dominance!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Sacrifice 8.5 hearts").color(GRAY));
    lore.add(Component.text("Gain resistance 20 and slowness 10 for 20 seconds").color(GRAY));
    lore.add(Component.text("After the effect is over").color(GRAY));
    lore.add(Component.text("Gain weakness 20 and wither 10 for 20 seconds").color(GRAY));
    item.lore(lore);
    return item;
  }

  @Override
  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "death_count");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("SBS", "SES", "SAS");
    recipe.setIngredient('S', Material.NETHERITE_SCRAP);
    recipe.setIngredient('B', Material.BELL);
    recipe.setIngredient('E', Material.BOOK);
    recipe.setIngredient('A', Material.SOUL_SAND);

    items.put("death_count", this);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onPlayerInteractEvent(PlayerInteractEvent event) {
    ItemStack deathCount = null;
    // Check both hands for the Ingot of Gambling
    for (ItemStack item :
        new ItemStack[] {
          event.getPlayer().getInventory().getItemInOffHand(),
          event.getPlayer().getInventory().getItemInMainHand()
        }) {
      ItemMeta meta = item.getItemMeta();
      if (meta != null && isItem(item, DeathCount.item)) {
        deathCount = item;
        break;
      }
    }
    if (deathCount != null) {
      if (event.getPlayer().getHealth() > 17) {
        event.getPlayer().setHealth(event.getPlayer().getHealth() - 17);
        event
            .getPlayer()
            .addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 19));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 9));
        getPlugin(Monolith.class)
            .getServer()
            .getScheduler()
            .runTaskLater(
                getPlugin(Monolith.class),
                () -> {
                  event
                      .getPlayer()
                      .addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 19));
                  event
                      .getPlayer()
                      .addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 1));
                },
                400L); // 60 ticks = 3 seconds
        event
            .getPlayer()
            .sendMessage(
                Component.text("The Death Count has granted you power")
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
