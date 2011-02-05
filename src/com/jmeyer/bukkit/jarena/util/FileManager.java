package com.jmeyer.bukkit.jarena.util;

import java.io.File;

import com.jmeyer.bukkit.jarena.JArenaPlugin;

/**
 * Handles files and directories of plug-in.
 * @author JMEYER
 **/
public class FileManager {

	/**
	 * Create directories for plug-in's files if not already made
	 */
	public static void createDirectoriesIfNotExists() {
		File rootDirectory = new File(JArenaPlugin.ROOT_DIRECTORY);
		File zoneDirectory = new File(JArenaPlugin.ZONE_DIRECTORY);
		
		if (!rootDirectory.exists())
			rootDirectory.mkdir();
		
		if (!zoneDirectory.exists())
			zoneDirectory.mkdir();
	}
	
}
