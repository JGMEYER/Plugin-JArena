package com.jmeyer.bukkit.jarena.listener;

import org.bukkit.event.block.BlockListener;

import com.jmeyer.bukkit.jarena.JArenaPlugin;

public class JArenaBlockListener extends BlockListener {
	
	private final JArenaPlugin plugin;

    public JArenaBlockListener(final JArenaPlugin plugin) {
        this.plugin = plugin;
    }

}