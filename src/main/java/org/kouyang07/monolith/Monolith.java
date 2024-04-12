package org.kouyang07.monolith;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.kouyang07.monolith.commands.GiveCommands;
import org.kouyang07.monolith.commands.LSCommands;
import org.kouyang07.monolith.listener.PlayerListener;

import java.util.Objects;
import java.util.logging.Level;
import java.io.File;

import static org.kouyang07.monolith.items.Combat.createTotemOfSafekeeping;

public final class Monolith extends JavaPlugin {

    public static TextColor SUCCESS_COLOR_GREEN = TextColor.color(0, 255, 0);
    public static TextColor SUCCESS_COLOR_RED = TextColor.color(255, 0, 0);

    @Override
    public void onEnable() {

        initalizeItems();

        registerListeners();

        registerCommands();

        getLogger().log(Level.INFO, "Monolith has been enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initalizeItems(){
        ItemStack totemOfSafekeeping = createTotemOfSafekeeping();

        NamespacedKey key = new NamespacedKey(this, "totem_of_safekeeping");
        ShapedRecipe recipe = new ShapedRecipe(key, totemOfSafekeeping);
        recipe.shape(" N ", "NTN", " N ");
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        Bukkit.addRecipe(recipe);
    }

    public void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        }

    public void registerCommands(){
        Objects.requireNonNull(getCommand("ls")).setExecutor(new LSCommands());
        getCommand("monocreate").setExecutor(new GiveCommands());
    }
}
