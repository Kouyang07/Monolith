package org.kouyang07.monolith;

import static org.bukkit.Bukkit.getPluginsFolder;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.kouyang07.monolith.commands.*;
import org.kouyang07.monolith.items.combat.armors.GolemChestplate;
import org.kouyang07.monolith.items.combat.armors.RageHelmet;
import org.kouyang07.monolith.items.combat.armors.SoldiersRepose;
import org.kouyang07.monolith.items.combat.armors.SpeedBoots;
import org.kouyang07.monolith.items.combat.misc.HeartOfTheUndead;
import org.kouyang07.monolith.items.combat.misc.TotemOfSafekeeping;
import org.kouyang07.monolith.items.combat.spells.BloodSacrifice;
import org.kouyang07.monolith.items.combat.spells.DeathCount;
import org.kouyang07.monolith.items.combat.spells.ZombieWand;
import org.kouyang07.monolith.items.combat.weapons.*;
import org.kouyang07.monolith.items.resources.*;
import org.kouyang07.monolith.listener.BorderListener;
import org.kouyang07.monolith.listener.DamageDisplayerListener;
import org.kouyang07.monolith.listener.LifeStealListener;
import org.kouyang07.monolith.listener.RandomSpawnListener;
import org.kouyang07.monolith.mechanics.CustomAttributes;
import org.kouyang07.monolith.mobs.Elite_Skeleton;
import org.kouyang07.monolith.mobs.Elite_Zombie;

public final class Monolith extends JavaPlugin {

  public static boolean debug = false;

  public static TextColor SUCCESS_COLOR_GREEN = TextColor.color(0, 255, 0);
  public static TextColor FAIL_COLOR_RED = TextColor.color(255, 0, 0);
  public static TextColor GRAY = TextColor.color(170, 170, 170);
  public static TextColor GOLD = TextColor.color(255, 215, 0);
  public static TextColor PURPLE = TextColor.color(170, 0, 170);
  public static Map<UUID, CustomAttributes> playerAttributes = new HashMap<>();

  @Override
  public void onEnable() {

    initalizeAttributes();

    registerListeners();

    registerCommands();

    initializeItems();

    getLogger().log(Level.INFO, "Monolith has been enabled");
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public void registerListeners() {
    getServer().getPluginManager().registerEvents(new LifeStealListener(), this);
    getServer().getPluginManager().registerEvents(new DamageDisplayerListener(), this);
    getServer().getPluginManager().registerEvents(new RandomSpawnListener(), this);
    getServer().getPluginManager().registerEvents(new BorderListener(), this);

    getServer().getPluginManager().registerEvents(new RecipeCommand(), this);

    getServer().getPluginManager().registerEvents(SwordOfGreed.getInstance(), this);
    getServer().getPluginManager().registerEvents(IngotOfGambling.getInstance(), this);
    getServer().getPluginManager().registerEvents(Claymore.getInstance(), this);
    getServer().getPluginManager().registerEvents(SonicCrossbow.getInstance(), this);

    getServer().getPluginManager().registerEvents(TotemOfSafekeeping.getInstance(), this);
    getServer().getPluginManager().registerEvents(HeartOfTheUndead.getInstance(), this);

    getServer().getPluginManager().registerEvents(BloodSacrifice.getInstance(), this);
    getServer().getPluginManager().registerEvents(DeathCount.getInstance(), this);
    getServer().getPluginManager().registerEvents(ZombieWand.getInstance(), this);

    getServer().getPluginManager().registerEvents(RageHelmet.getInstance(), this);
    getServer().getPluginManager().registerEvents(GolemChestplate.getInstance(), this);
    getServer().getPluginManager().registerEvents(SpeedBoots.getInstance(), this);
    getServer().getPluginManager().registerEvents(SoldiersRepose.getInstance(), this);

    getServer().getPluginManager().registerEvents(new CustomAttributes(), this);

    getServer().getPluginManager().registerEvents(Elite_Zombie.getInstance(), this);
    getServer().getPluginManager().registerEvents(Elite_Skeleton.getInstance(), this);
  }

  public void registerCommands() {
    Objects.requireNonNull(getCommand("ls")).setExecutor(new LSCommands());
    Objects.requireNonNull(getCommand("monocreate")).setExecutor(new GiveCommands());
    Objects.requireNonNull(getCommand("monospawn")).setExecutor(new SpawnCommands());
    Objects.requireNonNull(getCommand("skills")).setExecutor(new StatCommands());
    Objects.requireNonNull(getCommand("monorecipe")).setExecutor(new RecipeCommand());
  }

  public void initializeItems() {
    ZombiesHeart.register();
    CompactDiamond.register();
    CompactGold.register();
    CompactNetherite.register();
    CompactIron.register();
    CompactObsidian.register();

    GolemChestplate.register();
    RageHelmet.register();
    SpeedBoots.register();
    SoldiersRepose.register();

    BloodSacrifice.register();
    DeathCount.register();

    Claymore.register();
    IngotOfGambling.register();
    SonicCrossbow.register();
    SwordOfGreed.register();
    ZombieWand.register();

    TotemOfSafekeeping.register();
    HeartOfTheUndead.register();
  }

  public void initalizeAttributes() {
    File file = new File(getPluginsFolder(), "Monolith/playerAttributes.txt");
    if (!file.exists()) {
      getLogger().log(Level.INFO, "No attribute data found");
    } else {
      try {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
          String[] data = scanner.nextLine().split(":");
          UUID uuid = UUID.fromString(data[0]);
          String[] attributes = data[1].split(",");
          playerAttributes.put(
              uuid,
              new CustomAttributes(
                  Integer.parseInt(attributes[0]),
                  Integer.parseInt(attributes[1]),
                  Integer.parseInt(attributes[2])));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
