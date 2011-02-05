package com.jmeyer.bukkit.jarena.element;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BattleZone {

	private String name;
	private ArrayList<Block> blocks;
	
	/**
	 * Create BattleZone <name> with list of blocks in contained area
	 * @param name of BattleZone
	 * @param list of blocks in battle area
	 */
	public BattleZone (String name, ArrayList<Block> blocks) {
		this.name = name;
		this.blocks = blocks;
	}
	
	
	
	/**
	 * Gets name of BattleZone
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	
	
	/**
	 * Clear BattleZone playing area
	 */
	public void clear() {
		for (Block block : blocks)
			block.setType(Material.AIR);
	}
	
	/**
	 * Add Block to the designated playing area list
	 * @param Block to add
	 */
	public void add(Block block) {
		blocks.add(block);
	}
	
}
