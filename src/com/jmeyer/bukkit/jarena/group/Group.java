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
	
	/**
	 * Removes all entities that do not belong in group
	 */
	public void removeUnwantedMembers();
	
	
	
	// TODO: Move to own class
	/**
	 * Returns a random location within the boundaries given
	 * @param world - world where location exists
	 * @param xMin - min x of volume
	 * @param xMax - max x of volume
	 * @param yMin - min y of volume
	 * @param yMax - max y of volume
	 * @param zMin - min z of volume
	 * @param zMax - max z of volume
	 * @return new location within given parameters
	 */
	/*
	protected Location randomLocation(World world, double xMin, double xMax, double yMin, 
			double yMax, double zMin, double zMax) {
		double x, y, z;
		
		if ((xMin > xMax) || (yMin > yMax) || (zMin > zMax)) {
			LOG.log(Level.SEVERE, "[JArena] Could not generate random location, " + 
					"coordinates invalid.");
			return null;
		} else {
			x = (Math.random()*xMin) + (xMax-xMin);
			y = (Math.random()*yMin) + (yMax-yMin);
			z = (Math.random()*zMin) + (zMax-zMin);
		}
		
		return new Location(world, x, y, z);
	}
	*/
	
}
