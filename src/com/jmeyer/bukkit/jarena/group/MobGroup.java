package com.jmeyer.bukkit.jarena.group;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;

public class MobGroup extends Group {

	// TODO: finish MobGroup class
	
	/**
	 * Create group with an empty list of mobs
	 */
	public MobGroup() {
		super();
	}
	
	/**
	 * Create group with predefined list of members
	 * @param group
	 */
	public MobGroup(ArrayList<LivingEntity> group) {
		super(group);
		removeUnwantedEntities();
	}
	
	
	
	/** 
	 * Add new Player to group
	 * @param new group member
	 */
	@Override
	public void add(LivingEntity le) {
		if (le instanceof Creature) {
			Creature cr = (Creature)le;
			super.add(cr);
		}
	}
	
	/**
	 * Spawn all monsters at given location
	 */
	public void spawnAll(Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		spawnAllScattered(x, x, y, y, z, z);
	}
	
	/**
	 * Spawn all monsters within given volume
	 * @param xMin of volume
	 * @param xMax of volume
	 * @param yMin of volume
	 * @param yMax of volume
	 * @param zMin of volume
	 * @param zMax of volume
	 */
	public void spawnAllScattered(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
	}
	
	/**
	 * Removes all non-Creatures from group
	 */
	public void removeUnwantedEntities() {
		for (LivingEntity le : super.group)
			if (!(le instanceof Creature)) {
				super.remove(le);
			}
	}
	
}
