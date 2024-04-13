package org.kouyang07.monolith.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.kouyang07.monolith.Monolith;

public class SpawnCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("monospawn")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("elite_zombie")) {
                spawnEliteZombie(Bukkit.getPlayer(sender.getName()).getLocation());
                return true;
            } else if (args.length > 0 && args[0].equalsIgnoreCase("elite_skeleton")) {
                spawnEliteSkeleton(Bukkit.getPlayer(sender.getName()).getLocation());
                return true;
            }
        }
        return false;
    }

    private void spawnEliteZombie(Location location) {
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);

        // Set properties
        zombie.setPersistent(true);
        zombie.setAdult();
        zombie.setFireTicks(0);
        zombie.setShouldBurnInDay(false);
        zombie.setCanBreakDoors(false);
        zombie.setCanPickupItems(false);

        zombie.customName(Component.text("Elite Zombie").color(Monolith.SUCCESS_COLOR_RED));
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
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
    }
    private void spawnEliteSkeleton(Location location) {
        Skeleton skeleton = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);

        // Set properties
        skeleton.setPersistent(true);
        skeleton.setFireTicks(0);
        skeleton.setShouldBurnInDay(false);
        skeleton.setCanPickupItems(false);

        skeleton.customName(Component.text("Elite Skeleton").color(Monolith.SUCCESS_COLOR_RED));
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
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
    }


}
