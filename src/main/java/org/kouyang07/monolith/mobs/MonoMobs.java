package org.kouyang07.monolith.mobs;

import java.util.HashMap;
import java.util.Objects;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public abstract class MonoMobs implements Listener {

  public static HashMap<String, MonoMobs> mobs = new HashMap<>();

  public void spawn(Location location) {}
}
