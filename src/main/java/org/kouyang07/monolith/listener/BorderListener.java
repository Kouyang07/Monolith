package org.kouyang07.monolith.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BorderListener implements Listener {
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    // Check if placing an iron bar that could form the structure
    if (event.getBlockPlaced().getType() == Material.IRON_BARS) {
      if (isProtectedStructure(event.getBlockPlaced())) {
        event.setCancelled(true); // Prevent creation of the structure
      }
    }
    // Check if placing stone bricks that could form the base of the structure
    else if (event.getBlockPlaced().getType() == Material.STONE_BRICKS) {
      // Check the block above and the block two above
      if (isProtectedStructure(event.getBlockPlaced().getRelative(0, 1, 0))) {
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (isProtectedStructure(event.getBlock())) {
      event.setCancelled(true); // Prevent breaking if it's the protected structure
    }
  }

  @EventHandler
  public void onBlockPhysics(BlockPhysicsEvent event) {
    // This event can trigger when a block change could affect the structure
    if (event.getSourceBlock().getType() == Material.IRON_BARS
        || event.getSourceBlock().getType() == Material.STONE_BRICKS) {
      if (isProtectedStructure(event.getSourceBlock())) {
        event.setCancelled(true); // Prevent structure alteration from physics
      }
    }
  }

  private boolean isProtectedStructure(Block block) {
    // Check if the block is an iron bar with stone bricks below
    if (block.getType() == Material.IRON_BARS
        && block.getRelative(0, -1, 0).getType() == Material.STONE_BRICKS
        && block.getRelative(0, -2, 0).getType() == Material.STONE_BRICKS) {
      return true;
    }
    // Check the blocks below if they are part of the protected structure
    else if (block.getType() == Material.STONE_BRICKS
        && block.getRelative(0, 1, 0).getType() == Material.IRON_BARS
        && block.getRelative(0, -1, 0).getType() == Material.STONE_BRICKS) {
      return true;
    }
    // Check the lowest stone brick if it's part of the protected structure
    else if (block.getType() == Material.STONE_BRICKS
        && block.getRelative(0, 2, 0).getType() == Material.IRON_BARS
        && block.getRelative(0, 1, 0).getType() == Material.STONE_BRICKS) {
      return true;
    }
    return false;
  }
}
