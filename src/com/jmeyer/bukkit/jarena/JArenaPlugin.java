package com.jmeyer.bukkit.jarena;

import java.io.File;
import java.util.ArrayList;

import com.jmeyer.bukkit.jarena.element.BattleEvent;

public class JArenaPlugin {

	public static final String ROOT_DIRECTORY = "plugins" + File.separator + "JArena-Data";
	public static final String ZONE_DIRECTORY = ROOT_DIRECTORY + File.separator + "Zones" + File.separator;

	public static ArrayList<BattleEvent> battles;

}
