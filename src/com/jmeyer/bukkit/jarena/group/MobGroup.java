package com.jmeyer.bukkit.jarena.group;


import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

import com.jmeyer.bukkit.jarena.group.Mob.MobException;
import com.jmeyer.bukkit.jarena.util.LocationFactory;

public class MobGroup implements Group {

	private ArrayList<CreatureType> group;
	private ArrayList<Creature> spawned;
	
	/**
	 * Create group with an empty list of mobs
	 */
	public MobGroup() {
		this(new ArrayList<CreatureType>());
	}
	
	/**
	 * Create group with predefined list of members
	 * @param group
	 */
	public MobGroup(ArrayList<CreatureType> group) {
		this.group = group;
		this.spawned = new ArrayList<Creature>();
	}
	
	/** 
	 * Add new mob to group
	 * @param mob - new group member
	 */
	public void add (CreatureType ct) {
		if (ct != null)
			this.group.add(ct);
	}
	
	/**
	 * Remove mob from group
	 * @param mob - member to remove
	 */
	public void remove(CreatureType ct) {
		if (this.group.indexOf(ct) > -1)
			this.group.remove(ct);
	}
	
	/**
	 * Kill and remove spawned creature from group
	 */
	public void remove(Creature cr) {
		if (this.spawned.indexOf(cr) > -1)
			this.spawned.remove(cr);
	}
	
	/**
	 * Spawn all monsters at given location
	 * @param plugin - JArena main file
	 * @param player - player in desired world
	 * @param cWorld - world to spawn in
	 * @param loc - location to spawn mobs
	 * @throws MobException 
	 */
	public void spawnAllFixed(Player player, Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		spawnAllScattered(player, x, x, y, y, z, z);
	}
	
	/**
	 * Spawn all monsters within given points
	 * @param plugin - JArena main file
	 * @param player - player in desired world
	 * @param cWorld - world to spawn in
	 * @param loc1 - first corner of volume
	 * @param loc2 - second corner of volume
	 */
	public void spawnAllScattered(Player player, Location loc1, Location loc2) {
		double x1 = loc1.getX();
		double y1 = loc1.getY();
		double z1 = loc1.getZ();
		double x2 = loc2.getX();
		double y2 = loc2.getY();
		double z2 = loc2.getZ();
		spawnAllScattered(player, x1, x2, y1, y2, z1, z2);
	}
	
	/**
	 * Spawn all monsters within given volume
	 * @param plugin - JArena main file
	 * @param player - player in desired world
	 * @param cWorld - world to spawn in
	 * @param x1 - min x of volume
	 * @param x2 - max x of volume
	 * @param y1 - min y of volume
	 * @param y2 - max y of volume
	 * @param z1 - min z of volume
	 * @param z2 - max z of volume
	 */
	public void spawnAllScattered(Player player, double x1, double x2, double y1, double y2, 
			double z1, double z2) {
		
		CraftWorld cWorld = (org.bukkit.craftbukkit.CraftWorld)player.getWorld();

		for (CreatureType ct : this.group) {
			Location loc = LocationFactory.randomLocation(cWorld, x1, x2, y1, y2, z1, z2);
			
			Creature spawn = cWorld.spawnCreature(loc, ct);
			this.spawned.add(spawn);				
		}
	}

	/** 
	 * Return the total number of living, spawned creatures in the group. 
	 * @return number alive
	 */
	@Override
	public int numAlive() {
		int total = 0;
		
		for (Creature cr : this.spawned)
			if (cr.getHealth() < 0)
				++total;
		
		return total;
	}

	/**
	 * Kills all spawned mobs
	 */
	@Override
	public void killAll() {
		for (Creature cr : this.spawned) {
			cr.setHealth(0);
			this.spawned.remove(cr);
		}
	}

	/**
	 * Teleports all spawned mobs to given location
	 * @param loc - Location to teleport to
	 */
	@Override
	public void tpAll(Location loc) {
		for (Creature cr : this.spawned)
			cr.teleportTo(loc);
	}
	
	public ArrayList<CreatureType> getCreatureTypes() {
		return group;
	}
	
	public ArrayList<Creature> getSpawned() {
		return spawned;
	}
	
	public void disownSpawned() {
		spawned = new ArrayList<Creature>();
	}
	
}
