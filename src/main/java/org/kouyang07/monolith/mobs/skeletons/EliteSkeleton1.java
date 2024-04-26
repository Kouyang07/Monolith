package org.kouyang07.monolith.mobs.skeletons;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.debug;

import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.resources.drops.SkeletonsBone;
import org.kouyang07.monolith.mobs.MonoMobs;

public class EliteSkeleton1 extends MonoMobs implements Listener {
  @Getter private static final EliteSkeleton1 instance = new EliteSkeleton1();
  private static final Random random = new Random();

  public EliteSkeleton1() {
    MonoMobs.mobs.put("elite_skeleton", instance);
  }

  @Override
  public void spawn(Location location) {
    Skeleton skeleton = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);

    // Set properties
    skeleton.setPersistent(true);
    skeleton.setFireTicks(0);
    skeleton.setShouldBurnInDay(false);
    skeleton.setCanPickupItems(false);

    skeleton.customName(Component.text("[Lvl 1] Elite Skeleton").color(Monolith.FAIL_COLOR_RED));
    skeleton.setCustomNameVisible(true);

    // Set equipment
    ItemStack bow = new ItemStack(Material.BOW);
    bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
    bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
    skeleton.getEquipment().setItemInMainHand(bow);
    skeleton.getEquipment().setItemInMainHandDropChance(0.0F);

    ItemStack[] armor = {
      new ItemStack(org.bukkit.Material.DIAMOND_BOOTS),
      new ItemStack(org.bukkit.Material.DIAMOND_LEGGINGS),
      new ItemStack(org.bukkit.Material.DIAMOND_CHESTPLATE),
      new ItemStack(org.bukkit.Material.DIAMOND_HELMET)
    };
    skeleton.getEquipment().setArmorContents(armor);
    skeleton.getEquipment().setBootsDropChance(0.0F);
    skeleton.getEquipment().setLeggingsDropChance(0.0F);
    skeleton.getEquipment().setChestplateDropChance(0.0F);
    skeleton.getEquipment().setHelmetDropChance(0.0F);

    // Apply effects
    skeleton.addPotionEffect(
        new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
  }

  @EventHandler
  public void onCreatureSpawn(CreatureSpawnEvent event) {
    if (event.getEntity() instanceof Skeleton) {
      if (random.nextInt(20) == 0) { // 1 in 20 chance
        event.setCancelled(true);
        Location location = event.getEntity().getLocation();
        spawn(location);
      }
    }
  }

  @EventHandler
  private void onEntityDeathEvent(EntityDeathEvent event) {
    if (event.getEntity().customName() == null) return;
    if (Objects.equals(
        event.getEntity().customName(),
        Component.text("[Lvl 1] Elite Skeleton").color(Monolith.FAIL_COLOR_RED))) {
      int rng = ((int) (Math.random() * 21));
      if (rng == 10) {
        if (debug) {
          getLogger()
              .log(
                  Level.INFO,
                  "Skeleton's Bone was dropped at " + event.getEntity().getLocation().toString());
        }
        event.getDrops().add(SkeletonsBone.getItem());
      }
    }
  }
}
