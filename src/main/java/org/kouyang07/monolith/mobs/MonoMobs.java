package org.kouyang07.monolith.mobs;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.event.Listener;

public abstract class MonoMobs implements Listener {

  public static HashMap<String, MonoMobs> mobs = new HashMap<>();

  public void spawn(Location location) {}
}
