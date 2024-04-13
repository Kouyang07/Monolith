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
import static org.kouyang07.monolith.Monolith.GRAY;

public class TotemOfSafekeeping extends MonoItemsIO {
    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text("Totem of Safekeeping").color(PURPLE));
            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Keeps your inventory on death").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Consumes on death").color(GRAY));
        lore.add(Component.text("Only if on main or off-hand").color(GRAY));
        item.lore(lore);
        return item;
    }
    @Override
    public Recipe recipe(){
        ItemStack totemOfSafekeeping = create();
        NamespacedKey totemOfSafeKeepingKey = new NamespacedKey(getPlugin(Monolith.class), "totem_of_safekeeping");
        ShapedRecipe recipe = new ShapedRecipe(totemOfSafeKeepingKey, totemOfSafekeeping);
        recipe.shape(" N ", "NTN", " N ");
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        //Bukkit.addRecipe(recipe);
        return recipe;
    }
}
