package org.kouyang07.monolith.items.combat.spells;

import static org.kouyang07.monolith.Monolith.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
import org.kouyang07.monolith.items.combat.weapons.IngotOfGambling;
import org.kouyang07.monolith.items.resources.drops.ZombiesHeart;
import org.kouyang07.monolith.mechanics.cooldown.CoolDownItems;
import org.kouyang07.monolith.mechanics.cooldown.Cooldown;

public class ZombieWand extends MonoItems implements Listener {
  @Getter private static final ZombieWand instance = new ZombieWand();
  @Getter private static final ItemStack item = instance.create();
  @Getter static final Recipe recipe = instance.recipe();

  @Override
  public ItemStack create() {
    ItemStack item = new ItemStack(Material.STICK, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Zombie Wand").color(PURPLE));

      meta.addEnchant(Enchantment.DURABILITY, 1, false);
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Draws power from the undead!").color(GOLD));
    lore.add(Component.empty());
    lore.add(Component.text("Heal 4 hearts on right click").color(GRAY));
    item.lore(lore);

    items.put("zombie_wand", this);
    return item;
  }

  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "zombies_wand");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape(" H ", " H ", " S ");
    recipe.setIngredient('H', ZombiesHeart.getItem());
    recipe.setIngredient('S', Material.STICK);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onPlayerInteractEvent(PlayerInteractEvent event) {
    if (!event.getAction().isRightClick()) return;
    ItemStack zombieWand = null;
    // Check both hands for the Ingot of Gambling
    for (ItemStack item :
        new ItemStack[] {
          event.getPlayer().getInventory().getItemInOffHand(),
          event.getPlayer().getInventory().getItemInMainHand()
        }) {
      ItemMeta meta = item.getItemMeta();
      if (meta != null && IngotOfGambling.isItem(ZombieWand.getItem(), item)) {
        zombieWand = item;
        break;
      }
    }
    if (zombieWand == null) return;
    if (Cooldown.useable(event.getPlayer().getUniqueId(), CoolDownItems.zombie_wand)) {
      event
          .getPlayer()
          .sendMessage(
              Component.text("The Zombie Wand granted you health from the undead!")
                  .color(SUCCESS_COLOR_GREEN));
      applyWand(event.getPlayer());
      Cooldown.addCoolDown(event.getPlayer().getUniqueId(), CoolDownItems.zombie_wand, 15);
    } else {
      event
          .getPlayer()
          .sendMessage(Component.text("This item is currently on cooldown!").color(FAIL_COLOR_RED));
    }
  }

  private void applyWand(Player player) {
    player.addPotionEffect(
        new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, 1, false, false, false));
  }
}
