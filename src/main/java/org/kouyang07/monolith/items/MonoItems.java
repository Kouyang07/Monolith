package org.kouyang07.monolith.items;

import org.kouyang07.monolith.items.combat.weapons.IngotOfGambling;
import org.kouyang07.monolith.items.combat.weapons.SwordOfGreed;
import org.kouyang07.monolith.items.combat.weapons.TotemOfSafekeeping;
import org.kouyang07.monolith.items.combat.armors.GolemChestplate;
import org.kouyang07.monolith.items.combat.armors.SpeedBoots;

public class MonoItems {
    public static MonoItemsIO totemOfSafekeeping = new TotemOfSafekeeping();
    public static MonoItemsIO ingotOfGambling = new IngotOfGambling();
    public static MonoItemsIO swordOfGreed = new SwordOfGreed();

    public static MonoItemsIO golemChestplate = new GolemChestplate();
    public static MonoItemsIO speedBoots = new SpeedBoots();
}
