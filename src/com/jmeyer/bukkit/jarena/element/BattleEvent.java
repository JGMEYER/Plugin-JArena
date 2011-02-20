package com.jmeyer.bukkit.jarena.element;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

import com.jmeyer.bukkit.jarena.JArenaPlugin;
import com.jmeyer.bukkit.jarena.group.Mob;
import com.jmeyer.bukkit.jarena.group.MobGroup;
import com.jmeyer.bukkit.jarena.group.PlayerGroup;
import com.jmeyer.bukkit.jarena.util.LocationFactory;

public class BattleEvent implements Runnable {

	// TODO: add comments and organize methods
	// TODO: add rewards to be given after each round
	private final JArenaPlugin plugin;
	private Logger log;
	
	private String name;
	private PlayerGroup players;
	private int minPlayers;
	private int maxPlayers;
	private ArrayList<MobGroup> mobGroups;
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
	public BattleEvent(JArenaPlugin plugin, String name, PlayerGroup players, int minPlayers, int maxPlayers, ArrayList<MobGroup> mobGroups, BattleZone zone) {
		this.plugin = plugin;
		this.log = plugin.LOG;
		this.name = name;
		this.players = players;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.mobGroups = mobGroups;
		this.zone = zone;
		this.round = 0;
		this.ongoing = false;
	}
	
	public boolean isOngoing() {
		return this.ongoing;
	}
	
	public boolean start() {
		if (!this.ongoing && this.hasEnoughPlayers()) {
			this.broadcastMessage("Event has started!");
			this.zone.save();
			this.players.tpAll(LocationFactory.randomLocation(
					zone.getNorthEastBottom().getLocation(), 
					zone.getSouthWestBottom().getLocation())
					);// zone.getNorthEastBottom().getLocation());
			this.startNextRound();
			this.ongoing = true;
			log.log(Level.INFO, "[JArena] Event " + this.getName() + " started.");
			return true;
		}
		
		log.log(Level.INFO, "[JArena] Event " + this.getName() + " could not be started.");
		return false;
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
		// restore zone
		// Concurrent modification exception (likely with entity listener)
		if (ongoing) {
			this.killMobWave(this.round);
		}
		// tp players to lobby
		this.broadcastMessage("Event has ended.");
		this.zone.clear();
		this.zone.restore();
		players = new PlayerGroup();
		round = 0;
		this.ongoing = false;
	}
	
	/**
	 * Begins the next round
	 */
	public void startNextRound() {
		if (hasNextRound()) {
			this.zone.clear(); // TODO: add ability to clear map
			this.players.giveAllItem(Material.DIRT, 5);
			this.round++;
			spawnMobWave(round);
			this.sendMessageToParty("Round " + round);
		} else
			this.stop(true);
	}
	
	/**
	 * Spawns <round> wave of MobGroups
	 * @param round - wave number to spawn
	 */
	public void spawnMobWave(int round) {
		Player player = this.players.getPlayerGroup().get(0);
		Location loc1 = this.zone.getSouthWestBottom().getLocation(); // TODO: change to within boundaries of this.zone
		Location loc2 = this.zone.getNorthEastBottom().getLocation(); // TODO: change to within boundaries of this.zone
		
		this.mobGroups.get(round-1).spawnAllScattered(player, loc1, loc2);
	}
	
	public void killMobWave(int round) {
		MobGroup mg = this.mobGroups.get(round-1);
		ArrayList<Creature> crsToKill = mg.getSpawned();
		
		mg.disownSpawned();
		for (Creature cr : crsToKill) {
			cr.setHealth(0);
		}
	}
	
	/**
	 * Returns true if players have another round to play
	 * @return whether or not another round
	 */
	public boolean hasNextRound() {
		if ((round + 1) > this.mobGroups.size())
			return false;
		return true;
	}
	
	/**
	 * Send message to all players in party
	 * @param message to players
	 */
	public void sendMessageToParty(String message) {
		players.sendMessage(this.name + ": " + message);
	}
	
	public ArrayList<MobGroup> getMobGroups() {
		return this.mobGroups;
	}
	
	public PlayerGroup getPlayers() {
		return this.players;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRound() {
		return this.round;
	}
	
	public BattleZone getBattleZone() {
		return this.zone;
	}
	
	public boolean hasEnoughPlayers() {
		int numPlayers = this.players.getPlayerGroup().size();
		
		if ((numPlayers >= minPlayers) && (numPlayers <= maxPlayers))
			return true;
		
		return false;
	}
	
	public void addPlayer(Player player) {
		if (!ongoing)
			if (players.getPlayerGroup().indexOf(player) < 0) {
				this.players.add(player); // TODO: make return boolean to avoid unneccessary if statement
				this.sendMessageToParty(player.getName() + " has joined the party.");
			}
	}
	
	public void removePlayer(Player player) {
		this.removePlayer(player, "has been removed from the party.");
	}
	
	public void removePlayer(Player player, String message) {
		this.players.remove(player); // TODO: make return boolean to avoid unneccessary messages
		this.sendMessageToParty(player.getName() + " " + message);
	}
	
	public void broadcastMessage(String message) {
		this.plugin.broadcastMessage(this.name + ": " + message);
	}
	
	public String toString() {
		String ret = new String();
		ret += "Name:  " + this.name + "\n";
		ret += "Players: \n";
		for (Player player : this.players.getPlayerGroup())
			ret += "  " + player.getName() + "\n";
		ret += "MinPlayers:  " + this.minPlayers + "\n";
		ret += "MaxPlayers:  " + this.maxPlayers + "\n";
		ret += "MobGroups: \n";
		for (int i = 0; i < this.mobGroups.size(); i++) {
			for (CreatureType ct : this.mobGroups.get(i).getCreatureTypes())
				ret += "  " + (i+1) + ") " + ct.getName() + "\n";
			ret += "\n";
		}
		return ret;
	}
	
	@Override
	public void run() {
		startNextRound();
	}
}
