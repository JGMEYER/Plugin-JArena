package com.jmeyer.bukkit.jarena.element;

import org.bukkit.Location;

public class BattleZone extends BattleVolume {

	private String name;
	
	/**
	 * Creates a BattleZone with given name and corner dimensions
	 * @param name
	 * @param loc1
	 * @param loc2
	 */
	public BattleZone (String name, Location loc1, Location loc2) {
		super(loc1, loc2);
		this.name = name;
	}
	
	/**
	 * Gets name of BattleZone
	 * @return
	 */
	public String getName() {
		return name;
	}
	
}
