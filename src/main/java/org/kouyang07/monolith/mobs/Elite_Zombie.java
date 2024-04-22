package org.kouyang07.monolith.mobs;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.debug;

import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.resources.ZombiesHeart;

public class Elite_Zombie extends MonoMobs implements Listener {
  @Getter private static final Elite_Zombie instance = new Elite_Zombie();
  private static final Random random = new Random();

  public Elite_Zombie() {
    MonoMobs.mobs.put("elite_zombie", instance);
  }

  @Override
  public void spawn(Location location) {
    Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);

    // Set properties
    zombie.setPersistent(true);
    zombie.setAdult();
    zombie.setFireTicks(0);
    zombie.setShouldBurnInDay(false);
    zombie.setCanBreakDoors(false);
    zombie.setCanPickupItems(false);

    zombie.customName(Component.text("[Lvl 1] Elite Zombie").color(Monolith.FAIL_COLOR_RED));
    zombie.setCustomNameVisible(true);

    // Set equipment
    ItemStack sword = new ItemStack(org.bukkit.Material.DIAMOND_SWORD);
    sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
    sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
    zombie.getEquipment().setItemInMainHand(sword);
    zombie.getEquipment().setItemInMainHandDropChance(0.0F);

    ItemStack[] armor = {
      new ItemStack(org.bukkit.Material.DIAMOND_BOOTS),
      new ItemStack(org.bukkit.Material.DIAMOND_LEGGINGS),
      new ItemStack(org.bukkit.Material.DIAMOND_CHESTPLATE),
      new ItemStack(org.bukkit.Material.DIAMOND_HELMET)
    };
    zombie.getEquipment().setArmorContents(armor);
    zombie.getEquipment().setBootsDropChance(0.0F);
    zombie.getEquipment().setLeggingsDropChance(0.0F);
    zombie.getEquipment().setChestplateDropChance(0.0F);
    zombie.getEquipment().setHelmetDropChance(0.0F);

    // Apply effects
    zombie.addPotionEffect(
        new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
  }

  @EventHandler
  private void onEntityDeathEvent(EntityDeathEvent event) {
    if (event.getEntity().customName() == null) return;
    if (!event
        .getEntity()
        .getLastDamageCause()
        .getCause()
        .equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) return;
    if (Objects.equals(
        event.getEntity().customName(),
        Component.text("[Lvl 1] Elite Zombie").color(Monolith.FAIL_COLOR_RED))) {
      int rng = ((int) (Math.random() * 21));
      if (rng == 10) {
        if (debug) {
          getLogger()
              .log(
                  Level.INFO,
                  "Zombie's heart was dropped at " + event.getEntity().getLocation().toString());
        }
        event.getDrops().add(ZombiesHeart.getItem());
      }
    }
  }

  @EventHandler
  public void onCreatureSpawn(CreatureSpawnEvent event) {
    if (event.getEntity() instanceof Zombie) {
      if (random.nextInt(20) == 0) { // 1 in 20 chance
        event.setCancelled(true);
        Location location = event.getEntity().getLocation();
        spawn(location);
      }
    }
  }

  @EventHandler
  public void onEntityTarget(EntityTargetEvent event) {
    if (event.getEntity().customName() != null
        && Objects.requireNonNull(event.getEntity().customName())
            .contains(Component.text("[Lvl 1]"))) {
      if (!(event.getTarget() instanceof Player)
          || ((Player) event.getTarget()).getGameMode() == GameMode.CREATIVE) {
        event.setCancelled(true);
      }
    }
  }
}
