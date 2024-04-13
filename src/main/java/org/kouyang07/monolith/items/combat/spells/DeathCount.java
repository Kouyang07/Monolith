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

public class DeathCount extends MonoItemsIO {
    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Death Count").color(Monolith.PURPLE));
            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Sacrifice your blood for dominance!").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Sacrifice 8.5 hearts").color(GRAY));
        lore.add(Component.text("Gain resistance 20 and slowness 10 for 20 seconds").color(GRAY));
        lore.add(Component.text("After the effect is over").color(GRAY));
        lore.add(Component.text("Gain weakness 20 and wither 10 for 20 seconds").color(GRAY));
        item.lore(lore);
        return item;
    }

    @Override
    public Recipe recipe() {
        ItemStack item = create();
        NamespacedKey key = new NamespacedKey(getPlugin(Monolith.class), "death_count");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("SBS",
                "SES",
                "SAS");
        recipe.setIngredient('S', Material.NETHERITE_SCRAP);
        recipe.setIngredient('B', Material.BELL);
        recipe.setIngredient('E', Material.BOOK);
        recipe.setIngredient('A', Material.SOUL_SAND);
        //Bukkit.addRecipe(recipe);
        return recipe;
    }
}
