package com.jmeyer.bukkit.jarena.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.jmeyer.bukkit.jarena.JArenaPlugin;
import com.jmeyer.bukkit.jarena.element.BattleEvent;
import com.jmeyer.bukkit.jarena.element.BattleZone;

/**
 * Handles events for all Player related events for JArena
 * @author JMEYER
 */
public class JArenaPlayerListener extends PlayerListener {
	private final JArenaPlugin plugin;

	public JArenaPlayerListener(JArenaPlugin instance) {
        plugin = instance;
    }
	
	// TODO: if player dies, remove him from be and hashmap, if applicable
	
	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		BattleEvent be = plugin.findBattleEventWith(player);
		
		if (be != null && be.isOngoing()) {
			BattleZone bz = be.getBattleZone();
			Location pLoc = player.getLocation();
			double pX = pLoc.getX();
			double pY = pLoc.getY();
			double pZ = pLoc.getZ();
			
			if (pX < bz.getSouthWall()) {
				event.setCancelled(true);
			}
			
			if (pX > bz.getNorthWall()) {
				event.setCancelled(true);
			}
			
			if (pY < bz.getBottomWall()) {
				event.setCancelled(true);
			}
			
			if (pY > bz.getTopWall()) {
				event.setCancelled(true);
			}
			
			if (pZ < bz.getEastWall()) {
				event.setCancelled(true);
			}
			
			if (pZ > bz.getWestWall()) {
				event.setCancelled(true);
			}
		}
	}
	
	@Override
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		BattleEvent be = plugin.findBattleEventWith(player);
		
		if (be != null) {
			be.removePlayer(player, " has died and been removed from the party.");
			
			System.out.println("numAlive: " + be.getPlayers().numAlive());
			
			if (be.getPlayers().numAlive() <= 0)
				be.stop(false);
		}		
	}

}
