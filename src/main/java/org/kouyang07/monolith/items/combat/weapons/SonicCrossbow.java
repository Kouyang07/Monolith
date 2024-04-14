package org.kouyang07.monolith.items.combat.weapons;

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

public class SonicCrossbow extends MonoItemsIO{
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.CROSSBOW, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Sonic Crossbow").color(PURPLE));
            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Harnesses the power of the Warden").color(GOLD));
        lore.add(Component.text("Does 16 damage on shoot").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Right-click to use").color(GRAY));
        item.lore(lore);
        return item;
    }
    public Recipe recipe() {
        ItemStack item = create();
        NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "sonic_crossbow");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("DSD",
                "DCD",
                "GEG");
        recipe.setIngredient('D', Material.DISC_FRAGMENT_5);
        recipe.setIngredient('S', Material.SCULK_SHRIEKER);
        recipe.setIngredient('C', Material.CROSSBOW);
        recipe.setIngredient('E', Material.ECHO_SHARD);
        //Bukkit.addRecipe(recipe);
        return recipe;
    }
}
