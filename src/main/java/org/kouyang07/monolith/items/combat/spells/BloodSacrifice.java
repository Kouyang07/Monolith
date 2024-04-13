package org.kouyang07.monolith.items.combat.spells;

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

import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import static org.kouyang07.monolith.Monolith.GOLD;
import static org.kouyang07.monolith.Monolith.GRAY;

public class BloodSacrifice extends MonoItemsIO {
    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Blood Sacrifice").color(Monolith.PURPLE));
            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Sacrifice your blood for power!").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Sacrifice 5 hearts for a strength 3 buff for 4 seconds").color(GRAY));
        item.lore(lore);
        return item;
    }

    @Override
    public Recipe recipe() {
        ItemStack item = create();
        NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "blood_sacrifice");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("SBS",
                "BEB",
                "SBS");
        recipe.setIngredient('S', Material.SOUL_SAND);
        recipe.setIngredient('B', Material.BLAZE_ROD);
        recipe.setIngredient('E', Material.BOOK);
        //Bukkit.addRecipe(recipe);
        return recipe;
    }
}
