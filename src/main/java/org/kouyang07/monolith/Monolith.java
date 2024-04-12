package org.kouyang07.monolith;

import org.bukkit.plugin.java.JavaPlugin;
import org.kouyang07.monolith.commands.LSCommands;
import org.kouyang07.monolith.listener.PlayerListener;

import java.util.Objects;
import java.util.logging.Level;
import java.io.File;

public final class Monolith extends JavaPlugin {
    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        Objects.requireNonNull(getCommand("ls")).setExecutor(new LSCommands());

        getLogger().log(Level.INFO, "Monolith has been enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
