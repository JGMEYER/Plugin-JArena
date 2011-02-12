package com.jmeyer.bukkit.jarena.group;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerGroup implements Group {

	private ArrayList<Player> group;
	
	/**
	 * Create group with an empty list of members
	 */
	public PlayerGroup() {
		this.group = new ArrayList<Player>();
	}
	
	/**
	 * Create group with predefined list of members
	 * @param group
	 */
	public PlayerGroup(ArrayList<Player> group) {
		this.group = group;
	}
	
	/** 
	 * Add new Player to group
	 * @param new group member
	 */
	public void add(Player player) {
		this.group.add(player);
	}
	
	/** 
	 * Remove player from group
	 * @param new group member
	 */
	public void remove(Player player) {
		if (this.group.indexOf(player) > -1)
			this.group.remove(player);
	}
	
	/**
	 * Give each Player 1 of <material>
	 * @param material to give
	 */
	public void giveAllItem(Material material) {
		giveAllItem(material.getId());
	}
	
	/**
	 * Give each Player <amount> of <material>
	 * @param material to give
	 * @param amount of material to give
	 */
	public void giveAllItem(Material material, int amount) {
		giveAllItem(material.getId(), amount);
	}
	
	/**
	 * Give each Player 1 of <itemId>
	 * @param id of item to give
	 */
	public void giveAllItem(int itemId) {
		giveAllItem(itemId, 1);
	}
		
	/**
	 * Give each Player <amount> of <itemId>
	 * @param id of item to give
	 * @param amount of item to give
	 */
	public void giveAllItem(int itemId, int amount) {
		for (Player player : this.group)
			player.getWorld().dropItem(player.getLocation(), new ItemStack(itemId, amount, (byte) 0));
	}
	
	/**
	 * Send message to each Player in group
	 * @param message to display
	 */
	public void sendMessage(String message) {
		for (Player player : this.group)
			player.sendMessage(ChatColor.RED + "[JArena] " + message);
	}

	/** 
	 * Return the total number of living players in the group. 
	 * @return number alive
	 */
	@Override
	public int numAlive() {
		int total = 0;
		
		for (Player player : this.group)
			if (player.getHealth() < 0)
				++total;
		
		return total;
	}

	/**
	 * Kills all players in group
	 */
	@Override
	public void killAll() {
		for (Player player : this.group)
			player.setHealth(0);
	}

	/**
	 * Teleports all players to given location
	 * @param loc - Location to teleport to
	 */
	@Override
	public void tpAll(Location loc) {
		for (Player player : this.group)
			player.teleportTo(loc);
	}
	
}
