package org.kouyang07.monolith.items.combat.weapons;

import static org.bukkit.Bukkit.*;
import static org.kouyang07.monolith.Monolith.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItems;

public class SonicCrossbow extends MonoItems implements Listener {
  @Getter private static final SonicCrossbow instance = new SonicCrossbow();
  @Getter private static final ItemStack item = instance.create();
  @Getter private static final Recipe recipe = instance.recipe();

  public ItemStack create() {
    ItemStack item = new ItemStack(Material.CROSSBOW, 1);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.displayName(Component.text("Sonic Crossbow").color(PURPLE));
      item.setItemMeta(meta);
    }
    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Harnesses the power of the Warden").color(GOLD));
    lore.add(Component.empty());
    lore.add(
        Component.text("Does more damage the further away you are from the target!").color(GRAY));
    item.lore(lore);

    items.put("sonic_crossbow", this);
    return item;
  }

  public Recipe recipe() {
    ItemStack item = create();
    NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "sonic_crossbow");
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("DSD", "DCD", "GEG");
    recipe.setIngredient('D', Material.DISC_FRAGMENT_5);
    recipe.setIngredient('S', Material.SCULK_SHRIEKER);
    recipe.setIngredient('C', Material.CROSSBOW);
    recipe.setIngredient('E', Material.ECHO_SHARD);
    return recipe;
  }

  public static void register() {
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  private void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
    ProjectileSource shooter = event.getEntity().getShooter();
    if (shooter instanceof Player player) {
      if (debug) {
        getLogger().log(Level.INFO, "Projectile event triggered by " + player.getName());
      }
      if (isItem(player.getInventory().getItemInMainHand(), item)
          || isItem(player.getInventory().getItemInOffHand(), item)) {
        player.getInventory().addItem(new ItemStack(Material.ARROW));
        event.setCancelled(true);
        if (player.getInventory().contains(Material.SCULK_CATALYST)
            || player.getGameMode().equals(GameMode.CREATIVE)) {
          player.getInventory().removeItem(new ItemStack(Material.SCULK_CATALYST, 1));
          shootBeam(player);
        } else {
          player.sendMessage(
              Component.text("You need a Sculk Catalyst to use this weapon!")
                  .color(FAIL_COLOR_RED));
        }
      }
    }
  }

  @EventHandler
  private void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
    Player player = event.getPlayer();
    ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

    if (isItem(newItem, item)) {
      event.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 1));
      player.addPotionEffect(
          new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false, true));
      player.addPotionEffect(
          new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0, true, false, true));
    } else if (isItem(
        player.getInventory().getItem(event.getPreviousSlot()), SonicCrossbow.getItem())) {
      player.removePotionEffect(PotionEffectType.SPEED);
      player.removePotionEffect(PotionEffectType.WEAKNESS);
      event.getPlayer().getInventory().removeItem(new ItemStack(Material.ARROW, 1));
    }
  }

  private void shootBeam(Player player) {
    Location eye = player.getEyeLocation();
    Vector direction = eye.getDirection();

    for (int i = 0; i < 32; i++) { // Length of the beam
      Location point = eye.add(direction);
      player.getWorld().spawnParticle(Particle.END_ROD, point, 1, 0, 0, 0, 0.01);

      if (point.isBlock() && i > 3) {
        break;
      }
      // Check for and damage all Damageable entities except the shooter
      int finalI = i;
      point.getNearbyEntities(0.5, 0.5, 0.5).stream()
          .filter(entity -> entity instanceof Damageable && entity != player)
          .forEach(entity -> ((Damageable) entity).damage(16 * (finalI / 10.0)));
    }
  }
}
