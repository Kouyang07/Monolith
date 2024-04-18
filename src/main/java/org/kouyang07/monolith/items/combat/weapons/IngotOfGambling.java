package org.kouyang07.monolith.items.combat.weapons;

import static org.kouyang07.monolith.Monolith.*;
import static org.kouyang07.monolith.mechanics.cooldown.Cooldown.addCoolDown;
import static org.kouyang07.monolith.mechanics.cooldown.Cooldown.useable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import org.kouyang07.monolith.mechanics.cooldown.CoolDownItems;

@Getter
public class IngotOfGambling extends MonoItems implements Listener {
  @Getter private static final IngotOfGambling instance = new IngotOfGambling();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  public ItemStack create() {
    ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Ingot of Gambling").color(PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Chances for Weakness and Strength on right click!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Right-click to use").color(GRAY));
    item.lore(lore);

    items.put("ingot_of_gambling", this);
    return item;
  }

  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "ingot_of_gambling");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("GDG", "DGD", "GDG");
    recipe.setIngredient('G', Material.GOLD_BLOCK);
    recipe.setIngredient('D', Material.DIAMOND);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onPlayerInteractEvent(PlayerInteractEvent event) {
    ItemStack ingotOfGambling = null;
    // Check both hands for the Ingot of Gambling
    for (ItemStack item :
        new ItemStack[] {
          event.getPlayer().getInventory().getItemInOffHand(),
          event.getPlayer().getInventory().getItemInMainHand()
        }) {
      ItemMeta meta = item.getItemMeta();
      if (meta != null && IngotOfGambling.isItem(IngotOfGambling.getItem(), item)) {
        ingotOfGambling = item;
        break;
      }
    }

    if (ingotOfGambling != null) {
      if (!useable(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling)) {
        event
            .getPlayer()
            .sendMessage(
                Component.text("You may not use it yet, its on cooldown")
                    .color(Monolith.FAIL_COLOR_RED));
        return;
      }
      Random rand = new Random();
      int chance = rand.nextInt(100) + 1;

      if (chance <= 15) {
        // 15% chance to get Weakness 1
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 0));
        addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling, 90);
      } else if (chance <= 50) {
        // 35% chance to get Weakness 2
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 1));
        addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling, 90);
      } else if (chance <= 65) {
        // 15% chance to get Strength 1
        event
            .getPlayer()
            .addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0));
        addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling, 90);
      } else {
        // 35% chance to get Strength 2
        addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.ingot_of_gambling, 90);
        event
            .getPlayer()
            .addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 1));
      }
    }
  }
}
