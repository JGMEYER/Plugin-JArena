package com.jmeyer.bukkit.jarena.element;

import java.util.ArrayList;

import org.bukkit.Material;

import com.jmeyer.bukkit.jarena.group.MobGroup;
import com.jmeyer.bukkit.jarena.group.PlayerGroup;

public class BattleEvent {

	// TODO: add rewards to be given after each round
	private String name;
	private PlayerGroup players;
	private ArrayList<MobGroup> mobs;
	private BattleZone zone;
	private int round;
	private boolean ongoing;
	
	/**
	 * Create event with given name, players, mobs, and blocks on map
	 * @param name - event name
	 * @param players - party of players fighting in event
	 * @param mobs - creatures to be spawned with each round
	 * @param zone - area on map that will be used as the battle area
	 */
	public BattleEvent(String name, PlayerGroup players, ArrayList<MobGroup> mobs, BattleZone zone) {
		this.name = name;
		this.players = players;
		this.mobs = mobs;
		this.zone = zone;
		this.round = 0;
		start();
	}
	
	
	
	public boolean isOngoing() {
		return this.ongoing;
	}
	
	
	
	public void start() {
		this.zone.clear();
		// tp players to zone
		this.nextRound();
		this.ongoing = true;
	}
	
	/**
	 * Stops event and does not award players for completion
	 */
	public void stop() {
		stop(false);
	}
	
	/**
	 * Stops event and rewards players if victory considered
	 * @param victory - whether or not team victorious
	 */
	// players victorious - reward, else nothing
	public void stop(boolean victory) {
		//
		// clear zone
		// kill mobs
		// tp players to lobby
		this.ongoing = false;
	}
	
	/**
	 * Begins the next round
	 */
	public void nextRound() {
		if (hasNextRound()) {
			zone.clear();
			players.giveAllItem(Material.DIRT, 5);
			round++;
			// spawn mobs
			sendMessageToParty("Round " + round);
		} else
			stop(true);
	}
	
	/**
	 * Returns true if players have another round to play
	 * @return whether or not another round
	 */
	public boolean hasNextRound() {
		if ((round + 1) >= mobs.size())
			return false;
		return true;
	}
	
	/**
	 * Send message to all players in party
	 * @param message to players
	 */
	public void sendMessageToParty(String message) {
		players.sendMessage(name + ": " + message);
	}
	
}
