package org.kouyang07.monolith.items.combat;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.Monolith;
import org.kouyang07.monolith.items.MonoItemsIO;

import java.util.ArrayList;
import java.util.List;

import static org.kouyang07.monolith.Monolith.*;

public class IngotOfGambling extends MonoItemsIO{
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Ingot of Gambling").color(PURPLE));
            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Chances for Weakness and Strength on right click!").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Right-click to use").color(GRAY));
        item.lore(lore);
        return item;
    }
    public Recipe recipe() {
        ItemStack item = create();
        NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "ingot_of_gambling");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("GDG",
                "DGD",
                "GDG");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND);
        //Bukkit.addRecipe(recipe);
        return recipe;
    }
}
