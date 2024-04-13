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
import org.kouyang07.monolith.commands.StatCommands;
import org.kouyang07.monolith.items.MonoItems;
import org.kouyang07.monolith.items.cooldown.Cooldown;
import org.kouyang07.monolith.items.MonoItemsIO;
import org.kouyang07.monolith.items.combat.TotemOfSafekeeping;
import org.kouyang07.monolith.listener.MobListener;
import org.kouyang07.monolith.listener.players.*;
import org.kouyang07.monolith.mobs.SpawnCommands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getPluginsFolder;

public final class Monolith extends JavaPlugin {

    public static boolean debug = true;

    public static ArrayList<MonoItemsIO> monoItems = new ArrayList<>();

    public static TextColor SUCCESS_COLOR_GREEN = TextColor.color(0, 255, 0);
    public static TextColor SUCCESS_COLOR_RED = TextColor.color(255, 0, 0);
    public static TextColor GRAY = TextColor.color(170, 170, 170);
    public static TextColor GOLD = TextColor.color(255, 215, 0);
    public static TextColor PURPLE = TextColor.color(170, 0, 170);

    public static Map<UUID, ArrayList<Cooldown>> cooldownList = new HashMap<>();
    public static Map<UUID, CustomAttributes> playerAttributes = new HashMap<>();

    @Override
    public void onEnable() {

        initalizeItems();

        initalizeAttributes();

        registerListeners();

        registerCommands();

        getLogger().log(Level.INFO, "Monolith has been enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initalizeItems(){
        monoItems.add(MonoItems.ingotOfGambling);
        monoItems.add(MonoItems.totemOfSafekeeping);

        for(MonoItemsIO items : monoItems){
            getLogger().log(Level.INFO, "Registering " + items.getClass().getSimpleName());
            Bukkit.addRecipe(items.recipe());
        }
    }

    public void registerListeners(){
        getServer().getPluginManager().registerEvents(new Death(), this);
        getServer().getPluginManager().registerEvents(new MobListener(), this);
        getServer().getPluginManager().registerEvents(new Interaction(), this);
        getServer().getPluginManager().registerEvents(new Inventory(), this);
        getServer().getPluginManager().registerEvents(new Attack(), this);
        getServer().getPluginManager().registerEvents(new Move(), this);
    }

    public void registerCommands(){
        Objects.requireNonNull(getCommand("ls")).setExecutor(new LSCommands());
        Objects.requireNonNull(getCommand("monocreate")).setExecutor(new GiveCommands());
        Objects.requireNonNull(getCommand("monospawn")).setExecutor(new SpawnCommands());
        Objects.requireNonNull(getCommand("skills")).setExecutor(new StatCommands());
    }

    public void initalizeAttributes(){
        File file = new File(getPluginsFolder(), "Monolith/playerAttributes.txt");
        if(!file.exists()){
            getLogger().log(Level.INFO, "No attribute data found");
        }else{
            try {
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(":");
                    UUID uuid = UUID.fromString(data[0]);
                    String[] attributes = data[1].split(",");
                    playerAttributes.put(uuid, new CustomAttributes(Integer.parseInt(attributes[0]), Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
