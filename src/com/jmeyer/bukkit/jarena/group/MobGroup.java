package com.jmeyer.bukkit.jarena.group;


import java.util.ArrayList;

import net.minecraft.server.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Player;

import com.jmeyer.bukkit.jarena.JArenaPlugin;
import com.jmeyer.bukkit.jarena.group.Mob.MobException;
import com.jmeyer.bukkit.jarena.util.LocationFactory;

public class MobGroup implements Group {

	private ArrayList<Mob> group;
	private ArrayList<CraftLivingEntity> spawned;
	
	/**
	 * Create group with an empty list of mobs
	 */
	public MobGroup() {
		this(new ArrayList<Mob>());
	}
	
	/**
	 * Create group with predefined list of members
	 * @param group
	 */
	public MobGroup(ArrayList<Mob> group) {
		this.group = group;
		this.spawned = new ArrayList<CraftLivingEntity>();
	}
	
	/** 
	 * Add new mob to group
	 * @param mob - new group member
	 */
	public void add(Mob mob) {
		this.group.add(mob);
	}
	
	/**
	 * Remove mob from group
	 * @param mob - member to remove
	 */
	public void remove(Mob mob) {
		if (this.group.indexOf(mob) > -1)
			this.group.remove(mob);
	}
	
	/**
	 * Kill and remove spawned creature from group
	 */
	public void remove(CraftLivingEntity cle) {
		if (this.spawned.indexOf(cle) > -1)
			this.spawned.remove(cle);
	}
	
	/**
	 * Spawn all monsters at given location
	 * @param plugin - JArena main file
	 * @param player - player in desired world
	 * @param cWorld - world to spawn in
	 * @param loc - location to spawn mobs
	 * @throws MobException 
	 */
	public void spawnAll(JArenaPlugin plugin, Player player, CraftWorld cWorld, Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		spawnAllScattered(plugin, player, cWorld, x, x, y, y, z, z);
	}
	
	/**
	 * Spawn all monsters within given points
	 * @param plugin - JArena main file
	 * @param player - player in desired world
	 * @param cWorld - world to spawn in
	 * @param loc1 - first corner of volume
	 * @param loc2 - second corner of volume
	 */
	public void spawnAllScattered(JArenaPlugin plugin, Player player, CraftWorld cWorld, Location loc1, Location loc2) {
		double x1 = loc1.getX();
		double y1 = loc1.getY();
		double z1 = loc1.getZ();
		double x2 = loc2.getX();
		double y2 = loc2.getY();
		double z2 = loc2.getZ();
		spawnAllScattered(plugin, player, cWorld, x1, x2, y1, y2, z1, z2);
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
	public void spawnAllScattered(JArenaPlugin plugin, Player player, CraftWorld cWorld, 
			double x1, double x2, double y1, double y2, double z1, double z2) {

		World world = ((org.bukkit.craftbukkit.CraftWorld)player.getWorld()).getHandle();

		for (Mob mob : group) {	
			Location loc = LocationFactory.randomLocation(cWorld, x1, x2, y1, y2, z1, z2);
			CraftEntity newSpawn = null;
			
			try {
				newSpawn = mob.spawn(player, plugin);
				newSpawn.teleportTo(loc);
				world.a(newSpawn.getHandle());
				
				if (this.spawned.indexOf((CraftLivingEntity)newSpawn) < 0)
					this.spawned.add((CraftLivingEntity)newSpawn);
			} catch (MobException e) {
				e.printStackTrace();
			}
		}
	}

	/** 
	 * Return the total number of living, spawned creatures in the group. 
	 * @return number alive
	 */
	@Override
	public int numAlive() {
		int total = 0;
		
		for (CraftLivingEntity cle : spawned)
			if (cle.getHealth() < 0)
				++total;
		
		return total;
	}

	/**
	 * Kills all spawned mobs
	 */
	@Override
	public void killAll() {
		for (CraftLivingEntity cle : this.spawned) {
			cle.setHealth(0); // TODO: remove spawned mob afterwards
			spawned.remove(cle);
		}
	}

	/**
	 * Teleports all spawned mobs to given location
	 * @param loc - Location to teleport to
	 */
	@Override
	public void tpAll(Location loc) {
		for (CraftLivingEntity cle : this.spawned)
			cle.teleportTo(loc);
	}
	
	public ArrayList<Mob> getMobs() {
		return group;
	}
	
	public ArrayList<CraftLivingEntity> getSpawned() {
		return spawned;
	}
	
	public void disownSpawned() {
		spawned = new ArrayList<CraftLivingEntity>();
	}
	
}
