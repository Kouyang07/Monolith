package org.kouyang07.monolith.items.combat.armors;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kouyang07.monolith.items.MonoItemsIO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.kouyang07.monolith.Monolith.*;

public class GolemChestplate extends MonoItemsIO {
    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Golem Chestplate").color(PURPLE));
            item.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Makes you immovable like a golem!").color(GOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Gives slowness 1").color(GRAY));
        item.lore(lore);
        return item;
    }
}
