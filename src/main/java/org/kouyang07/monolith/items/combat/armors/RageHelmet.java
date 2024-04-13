package org.kouyang07.monolith.items.combat.armors;

import net.kyori.adventure.text.Component;
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

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import static org.kouyang07.monolith.Monolith.*;

public class RageHelmet extends MonoItemsIO {
    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Rage Helmet").color(PURPLE));
            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("No pain no gain!").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Gives + 0.5 damage per heart lost").color(GRAY));
        item.lore(lore);
        return item;
    }
    @Override
    public Recipe recipe() {
        ItemStack item = create();
        NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "rage_helmet");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("N N",
                "BHB",
                "BBB");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.BLAZE_ROD);
        recipe.setIngredient('H', Material.NETHERITE_HELMET);
        //Bukkit.addRecipe(recipe);
        return recipe;
    }
}
