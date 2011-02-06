package com.jmeyer.bukkit.jarena.group;

import org.bukkit.Location;

/**
 * Outlines basic commands for battle groups.
 * @author JMEYER
 **/
public interface Group {
	
	/** 
	 * Return the total number of living members in the group. 
	 * @return number alive
	 */
	public int numAlive();
	
	/**
	 * Kill all members of the group.
	 */
	public void killAll();
	
	/**
	 * Teleport all members to given location
	 * @param loc - location to tp to
	 */
	public void tpAll(Location loc);
	
}
