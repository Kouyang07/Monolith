package org.kouyang07.monolith.listener;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.debug;

import java.awt.*;
import java.util.logging.Level;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.kouyang07.monolith.items.MonoItems;

public class ItemPlaceListener implements Listener {
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    final String plain =
        PlainTextComponentSerializer.plainText().serialize(event.getItemInHand().displayName());
    String itemid =
        plain
            .toLowerCase()
            .replace(" ", "_")
            .replace("'", "")
            .replace("[", "")
            .replace("]", "")
            .trim();
    if (debug) {
      getLogger()
          .log(
              Level.INFO,
              "Item placed: " + event.getBlockPlaced().getType() + " with id " + itemid);
    }
    if (MonoItems.items.containsKey(itemid)) {
      event.setCancelled(true);
    }
  }
}
