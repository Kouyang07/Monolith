package org.kouyang07.monolith.items.combat.weapons;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItemsIO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.kouyang07.monolith.Monolith.*;

public class SwordOfGreed extends MonoItemsIO {
    @Override
    public ItemStack create(){
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Sword of Greed").color(PURPLE));
            AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            AttributeModifier speedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 0.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifier);

            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Only the rich of the rich can wield this sword!").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Costs 1 diamond per hit").color(GRAY));
        item.lore(lore);
        return item;
    }

    public Recipe recipe() {
        ItemStack item = create();
        NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "sword_of_greed");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("DDD",
                "DSG",
                "GGG");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('S', Material.DIAMOND_SWORD);
        //Bukkit.addRecipe(recipe);
        return recipe;
    }
}
