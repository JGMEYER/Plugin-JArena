package com.jmeyer.bukkit.jarena.group;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerGroup extends Group {

	/**
	 * Create group with an empty list of members
	 */
	public PlayerGroup() {
		super();
	}
	
	/**
	 * Create group with predefined list of members
	 * @param group
	 */
	public PlayerGroup(ArrayList<LivingEntity> group) {
		super(group);
		removeUnwantedEntities();
	}
	
	
	
	/** 
	 * Add new Player to group
	 * @param new group member
	 */
	@Override
	public void add(LivingEntity le) {
		if (le instanceof Player) {
			Player player = (Player)le;
			super.add(player);
		}
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
		for (LivingEntity le : super.group)
			if (le instanceof Player) {
				Player player = (Player)le;
				player.getWorld().dropItem(player.getLocation(), new ItemStack(itemId, amount, (byte) 0));
			}
	}
	
	/**
	 * Send message to each Player in group
	 * @param message to display
	 */
	public void sendMessage(String message) {
		for (LivingEntity le : super.group)
			if (le instanceof Player) {
				Player player = (Player)le;
				player.sendMessage(ChatColor.RED + "[JArena] " + message);
			}
	}
	
	/**
	 * Removes all non-Players from group
	 */
	public void removeUnwantedEntities() {
		for (LivingEntity le : super.group)
			if (!(le instanceof Player)) {
				remove(le);
			}
	}
	
}
