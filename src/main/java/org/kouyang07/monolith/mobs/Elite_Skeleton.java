package org.kouyang07.monolith.mobs;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;

public class Elite_Skeleton extends MonoMobs {
  @Getter private static final Elite_Skeleton instance = new Elite_Skeleton();

  public Elite_Skeleton() {
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

    skeleton.customName(Component.text("Elite Skeleton").color(Monolith.FAIL_COLOR_RED));
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
}
