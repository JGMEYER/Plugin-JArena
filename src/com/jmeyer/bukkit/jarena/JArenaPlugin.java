package com.jmeyer.bukkit.jarena;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.jmeyer.bukkit.jarena.element.BattleEvent;
import com.jmeyer.bukkit.jarena.element.BattleZone;
import com.jmeyer.bukkit.jarena.group.MobGroup;
import com.jmeyer.bukkit.jarena.group.PlayerGroup;
import com.jmeyer.bukkit.jarena.listener.JArenaBlockListener;
import com.jmeyer.bukkit.jarena.listener.JArenaEntityListener;
import com.jmeyer.bukkit.jarena.listener.JArenaPlayerListener;

public class JArenaPlugin extends JavaPlugin {

	public static final Logger LOG = Logger.getLogger("Minecraft");
	public static final String ROOT_DIRECTORY = "plugins" + File.separator
			+ "JArena-Data";
	public static final String ZONE_DIRECTORY = ROOT_DIRECTORY + File.separator
			+ "Zones" + File.separator;
	
	private final JArenaBlockListener blockListener = new JArenaBlockListener(this);
	private final JArenaEntityListener entityListener = new JArenaEntityListener(this);
	private final JArenaPlayerListener playerListener = new JArenaPlayerListener(this);
	private final HashMap<Player, Boolean> DEBUGEES = new HashMap<Player, Boolean>();
	private final HashMap<Player, BattleEvent> BATTLERS = new HashMap<Player, BattleEvent>();
	
	public static ArrayList<BattleEvent> battles;
	public static ArrayList<MobGroup> mobGroups;
	
	/**
	 * Step through preliminary setup when plugin enabled
	 */
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		PluginDescriptionFile pdfFile = this.getDescription();
		battles = new ArrayList<BattleEvent>();
		mobGroups = new ArrayList<MobGroup>();
		
		pm.registerEvent(Event.Type.BLOCK_PLACED, blockListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Priority.Normal, this);
		
		// setup all events ====================================================
		String name = "Test";
		List<World> worlds = this.getServer().getWorlds();
		PlayerGroup pg = new PlayerGroup();
		MobGroup mg1 = new MobGroup();
		  mg1.add(CreatureType.SKELETON);
		  mg1.add(CreatureType.COW);
		  mg1.add(CreatureType.COW);
		  mg1.add(CreatureType.COW);
		MobGroup mg2 = new MobGroup();
		  mg2.add(CreatureType.SKELETON);
		  mg2.add(CreatureType.ZOMBIE);
		  mg2.add(CreatureType.ZOMBIE);
		MobGroup mg3 = new MobGroup();
		  mg3.add(CreatureType.SKELETON);
		  mg3.add(CreatureType.SKELETON);
		  mg3.add(CreatureType.ZOMBIE);
		  mg3.add(CreatureType.ZOMBIE);
		MobGroup mg4 = new MobGroup();
		  mg4.add(CreatureType.SKELETON);
		  mg4.add(CreatureType.SKELETON);
		  mg4.add(CreatureType.SKELETON);
		  mg4.add(CreatureType.SPIDER);
		MobGroup mg5 = new MobGroup();
		  mg5.add(CreatureType.SPIDER);
		  mg5.add(CreatureType.SKELETON);
		  mg5.add(CreatureType.SKELETON);
		  mg5.add(CreatureType.ZOMBIE);
		  mg5.add(CreatureType.ZOMBIE);
		ArrayList<MobGroup> mgs = new ArrayList<MobGroup>();
		  mgs.add(mg1);
		  mgs.add(mg2);
		  mgs.add(mg3);
		  mgs.add(mg4);
		  mgs.add(mg5);
		Location loc1 = new Location (worlds.get(0), -181, 66, 334);
		Location loc2 = new Location (worlds.get(0), -187, 68, 327);
		BattleZone bz = new BattleZone(name, loc1, loc2);
		
		BattleEvent be = new BattleEvent(this, name, pg, 1, 3, mgs, bz);
		battles.add(be);
		System.out.println(be.toString());
		// =====================================================================
		
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}	
	
	/**
	 * Step through final calls when plugin disabled
	 */
	@Override
	public void onDisable() {
		for (BattleEvent be : battles) {
			be.stop(false);
		}
	}
	
	/**
	 * Handles commands corresponding to plugin
	 * @return command successful
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		String[] trimmedArgs = args;
        String commandName = command.getName().toLowerCase();
        
        if (commandName.equals("addme")) {
        	return addPlayer(sender, trimmedArgs);
        } else if (commandName.equals("starttest")) {
        	return startTest(sender, trimmedArgs);
        }
        
        return false;
	}
	
	// TODO: make it take a string as a parameter to correspond to BattleEvent name
	// TODO: use with "/jarena joinevent" or sth
	public boolean addPlayer(CommandSender sender, String[] trimmedArgs) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			battles.get(0).addPlayer(player);
			for (Player p : battles.get(0).getPlayers().getPlayerGroup()) {
				System.out.println(p.getName());
			}
		}
		return false;
	}
	
	public boolean startTest(CommandSender sender, String[] trimmedArgs) {
		return battles.get(0).start();
	}
	
	/**
	 * Capitalizes first letter of string and sets rest to lower case
	 * @param s - String input
	 * @return cleaned String
	 */
	private String capitalCase(String s){
    	return s.toUpperCase().charAt(0) + s.toLowerCase().substring(1);
    }
	
	/**
	 * Check if player is debugging plugin
	 * @param player - Player to check
	 * @return whether or not player is debugging
	 */
	public boolean isDebugging(final Player player) {
        if (DEBUGEES.containsKey(player)) {
            return DEBUGEES.get(player);
        } else {
            return false;
        }
    }

	/**
	 * Add or remove player to HashMap of debuggers for plugin
	 * @param player - Player to add
	 * @param value - whether or not debugging
	 */
    public void setDebugging(final Player player, final boolean value) {
        DEBUGEES.put(player, value);
    }

    public BattleEvent findBattleEventWith(Creature cr) {
    	for (BattleEvent be : battles) {
    		ArrayList<MobGroup> mobGroups = be.getMobGroups();
    		
    		for (MobGroup mg : mobGroups)
    			if (mg.getSpawned().indexOf(cr) >= 0)
    				return be;
    	}
    	
    	return null;
    }
    
    // TODO: use hashmap, instead
    public BattleEvent findBattleEventWith(Player player) {
    	for (BattleEvent be : battles)
    		if (be.getPlayers().getPlayerGroup().indexOf(player) >= 0)
    			return be;
    	
    	return null;
    }
    
    public boolean isLastMobInRoundOfEvent(Creature cr, BattleEvent be) {
    	MobGroup mg = be.getMobGroups().get(be.getRound()-1);
    	ArrayList<Creature> spawned = mg.getSpawned();
    	
    	if (spawned.indexOf(cr) >= 0)
    		if (spawned.size() <= 1)
    			return true;
    	
    	return false;
    }
    
    public void broadcastMessage(String message) {
    	this.getServer().broadcastMessage(ChatColor.DARK_RED + "[JArena] " + message);
    }
    
}
