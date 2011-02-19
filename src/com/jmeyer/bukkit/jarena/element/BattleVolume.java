package com.jmeyer.bukkit.jarena.element;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BattleVolume {

	private Block northWestTop;
	private Block northWestBottom;
	private Block northEastTop;
	private Block northEastBottom;
	private Block southEastTop;
	private Block southEastBottom;
	private Block southWestTop;
	private Block southWestBottom;
	private ArrayList<Block> blocks;
	
	/**
	 * Creates a controlled volume bounded by the coordinates of two points
	 * @param loc1
	 * @param loc2
	 */
	public BattleVolume (Location loc1, Location loc2) {
		World world = loc1.getWorld();
		int x1 = (int)(loc1.getX());
		int x2 = (int)(loc2.getX());
		int y1 = (int)(loc1.getY());
		int y2 = (int)(loc2.getY());
		int z1 = (int)(loc1.getZ());
		int z2 = (int)(loc2.getZ());
		
		if (x1 > x2) {
			int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		
		if (y1 > y2) {
			int temp = y1;
			y1 = y2;
			y2 = temp;				
		}
		
		if (z1 > z2) {
			int temp = z1;
			z1 = z2;
			z2 = temp;				
		}
		
		this.northWestTop = world.getBlockAt(x2, y2, z2);
		this.northWestBottom = world.getBlockAt(x2, y1, z2);
		this.northEastTop = world.getBlockAt(x2, y2, z1);
		this.northEastBottom = world.getBlockAt(x2, y1, z1);
		this.southEastTop = world.getBlockAt(x1, y2, z1);
		this.southEastBottom = world.getBlockAt(x1, y1, z1);
		this.southWestTop = world.getBlockAt(x1, y2, z2);
		this.southWestBottom = world.getBlockAt(x1, y1, z2);
		this.blocks = new ArrayList<Block>();
	}
	
	public void save() {
		// TODO: figure out way to save. cannot use aliases.
	}
	
	public void restore() {
		// TODO: configure volume restore
	}
	
	/**
	 * Sets all blocks within inner volume to air
	 */
	public void clear() {
		World world = northWestTop.getWorld();
		int xMin = (int) getSouthWall();
		int xMax = (int) getNorthWall();
		int yMin = (int) getBottomWall();
		int yMax = (int) getTopWall();
		int zMin = (int) getEastWall();
		int zMax = (int) getWestWall();
		
		/*
		for (int y = yMax; y >= yMin; --y) {
			for (int z = zMin+1; z <= zMax-1; ++z) {
				for (int x = xMin+1; x <= xMax-1; ++x) {
					world.getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
		*/
		
		for (int y = yMax; y >= yMin; --y) {
			for (int z = zMin; z <= zMax; ++z) {
				for (int x = xMin; x <= xMax; ++x) {
					world.getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
	}
	
	/**
	 * Get northern coordinate of volume
	 */
	public double getNorthWall() {
		return northWestTop.getX();
	}
	
	/**
	 * Get eastern coordinate of volume
	 */
	public double getEastWall() {
		return northEastTop.getZ();
	}
	
	/**
	 * Get southern coordinate of volume
	 */
	public double getSouthWall() {
		return southEastTop.getX();
	}
	
	/**
	 * Get western coordinate of volume
	 */
	public double getWestWall() {
		return northWestTop.getZ();
	}
	
	/**
	 * Get top coordinate of volume
	 */
	public double getTopWall() {
		return northWestTop.getY();
	}
	
	/**
	 * Get bottom coordinate of volume
	 */
	public double getBottomWall() {
		return northWestBottom.getY();
	}
	
	/**
	 * Get top northwestern point of volume
	 */
	public Block getNorthWestTop() {
		return northWestTop;
	}
	
	/**
	 * Get bottom northwestern point of volume
	 */
	public Block getNorthWestBottom() {
		return northWestBottom;
	}
	
	/**
	 * Get top northeastern point of volume
	 */
	public Block getNorthEastTop() {
		return northEastTop;
	}
	
	/**
	 * Get bottom northeastern point of volume
	 */
	public Block getNorthEastBottom() {
		return northEastBottom;
	}
	
	/**
	 * Get top southeastern point of volume
	 */
	public Block getSouthEastTop () {
		return southEastTop;
	}
	
	/**
	 * Get bottom southeastern point of volume
	 */
	public Block getSouthEastBottom () {
		return southEastBottom;
	}
	
	/**
	 * Get top southwestern point of volume
	 */
	public Block getSouthWestTop() {
		return southWestTop;
	}
	
	/**
	 * Get bottom southwestern point of volume
	 */
	public Block getSouthWestBottom() {
		return southWestBottom;
	}
	
}
