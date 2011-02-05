package com.jmeyer.bukkit.jarena.group;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * Outlines basic commands for entity groups.
 * @author JMEYER
 **/
public class Group {

	// TODO: should this really be just a class? - (interface, abstract?)
	ArrayList<LivingEntity> group;
	
	/**
	 * Instantiate new group with empty list.
	 */
	public Group() {
		this.group = new ArrayList<LivingEntity>();
	}
	
	/**
	 * Instantiate new group with given list.
	 * @param list of LivingEntitys in group
	 */	
	public Group(ArrayList<LivingEntity> group) {
		this.group = group;
	}
	
	
	
	/** 
	 * Return the total number of living entities in the group. 
	 * @return number alive
	 */
	public int numAlive() { 
		int total = 0;
		
		for (LivingEntity le : group)
			if (le.getHealth() > 0)
				total++;
		
		return total; 
	}
	
	/**
	 * Kill all entities in the group.
	 */
	public void killAll() {
		for (LivingEntity le : group)
			le.setHealth(0);
	}
	
	/**
	 * Teleport all members to given location
	 * @param location to tp to
	 */
	public void tpAll(Location loc) {
		for (LivingEntity le : group)
			le.teleportTo(loc);
	}
	
	/** 
	 * Add new LivingEntity to group
	 * @param new group member
	 */
	public void add(LivingEntity le) {
		group.add(le);
	}
	
	/**
	 * Remove LivingEntity from group
	 * @param rejected group member
	 */
	public void remove(LivingEntity le) {
		group.remove(le);
	}
	
	/**
	 * Removes all entities that do not belong in group
	 */
	public void removeUnwantedEntities() {}
	
}
