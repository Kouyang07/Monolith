package org.kouyang07.monolith.mobs;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kouyang07.monolith.Monolith;

public class Elite_Zombie extends MonoMobs {
  @Getter private static final Elite_Zombie instance = new Elite_Zombie();

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

    zombie.customName(Component.text("Elite Zombie").color(Monolith.FAIL_COLOR_RED));
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
}
