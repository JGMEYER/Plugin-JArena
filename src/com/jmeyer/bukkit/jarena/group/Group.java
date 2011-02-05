package com.jmeyer.bukkit.jarena.group;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;

/**
 * Outlines basic commands for entity groups.
 * @author JMEYER
 **/
public class Group {

	protected static final Logger LOG = Logger.getLogger("Minecraft");
	// TODO: should this really be just a class? - (interface, abstract?)
	ArrayList<CraftLivingEntity> group;
	
	/**
	 * Instantiate new group with empty list.
	 */
	public Group() {
		this.group = new ArrayList<CraftLivingEntity>();
	}
	
	/**
	 * Instantiate new group with given list.
	 * @param list of LivingEntitys in group
	 */	
	public Group(ArrayList<CraftLivingEntity> group) {
		this.group = group;
	}
	
	
	
	/** 
	 * Return the total number of living entities in the group. 
	 * @return number alive
	 */
	public int numAlive() { 
		int total = 0;
		
		for (CraftLivingEntity cle : group)
			if (cle.getHealth() > 0)
				total++;
		
		return total; 
	}
	
	/**
	 * Kill all entities in the group.
	 */
	public void killAll() {
		for (CraftLivingEntity cle : group)
			cle.setHealth(0);
	}
	
	/**
	 * Teleport all members to given location
	 * @param location to tp to
	 */
	public void tpAll(Location loc) {
		for (CraftLivingEntity cle : group)
			cle.teleportTo(loc);
	}
	
	/** 
	 * Add new LivingEntity to group
	 * @param new group member
	 */
	public void add(CraftLivingEntity cle) {
		group.add(cle);
	}
	
	/**
	 * Remove LivingEntity from group
	 * @param rejected group member
	 */
	public void remove(CraftLivingEntity cle) {
		group.remove(cle);
	}
	
	/**
	 * Removes all entities that do not belong in group
	 */
	public void removeUnwantedEntities() {}
	
	
	
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
	
}
