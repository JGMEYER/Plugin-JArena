package com.jmeyer.bukkit.jarena.listener;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.jmeyer.bukkit.jarena.JArenaPlugin;
import com.jmeyer.bukkit.jarena.element.BattleEvent;

public class JArenaEntityListener extends EntityListener {
	
	private final JArenaPlugin plugin;

    public JArenaEntityListener(final JArenaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void onEntityDeath (EntityDeathEvent event) {
    	Entity entity = event.getEntity();
    	
    	if (entity instanceof CraftLivingEntity) {
    		CraftLivingEntity cle = (CraftLivingEntity)entity;
    		Creature cr = null;
    		BattleEvent be = null;
    		
    		if (cle instanceof Creature) {
    			cr = (Creature)cle;
    			be = plugin.findBattleEventWith(cr);
    			
    			if (be != null) {
    				boolean last = plugin.isLastMobInRoundOfEvent(cr, be);
    				be.getMobGroups().get(be.getRound()-1).remove(cr);
        			
        			if (last)
        				be.startNextRound();
        		}
    		}
    		
    	}
    }
	
}
