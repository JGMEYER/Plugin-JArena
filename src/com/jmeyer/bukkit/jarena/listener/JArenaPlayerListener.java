package com.jmeyer.bukkit.jarena.listener;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftGhast;
import org.bukkit.craftbukkit.entity.CraftZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

import com.jmeyer.bukkit.jarena.JArenaPlugin;
import com.jmeyer.bukkit.jarena.group.MobGroup;

/**
 * Handles events for all Player related events for JArena
 * @author JMEYER
 */
public class JArenaPlayerListener extends PlayerListener {
	private final JArenaPlugin plugin;

	public JArenaPlayerListener(JArenaPlugin instance) {
        plugin = instance;
    }

}
