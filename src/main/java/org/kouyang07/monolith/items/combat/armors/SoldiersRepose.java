package org.kouyang07.monolith.items.combat.armors;

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

public class SoldiersRepose extends MonoItemsIO {
    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Solder's Repose").color(PURPLE));
            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Stand strong!").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Gives the following effect while worn").color(GRAY));
        lore.add(Component.text("Resistance 1").color(GRAY));
        lore.add(Component.text("Regeneration 1").color(GRAY));
        lore.add(Component.text("Slowness 10").color(GRAY));
        lore.add(Component.text("Weakness 3").color(GRAY));
        item.lore(lore);
        return item;
    }
    @Override
    public Recipe recipe() {
        ItemStack item = create();
        NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "soldiers_repose");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("DBD",
                "DGD",
                "D D");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('B', Material.DIAMOND_BLOCK);
        recipe.setIngredient('G', Material.GOLDEN_APPLE);
        //Bukkit.addRecipe(recipe);
        return recipe;
    }
}
