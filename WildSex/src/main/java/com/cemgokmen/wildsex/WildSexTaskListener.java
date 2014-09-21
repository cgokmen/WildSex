package com.cemgokmen.wildsex;

import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.java.JavaPlugin;

public class WildSexTaskListener implements Listener {
    private static final boolean DEBUG_MODE = false;
    private JavaPlugin plugin;

    public WildSexTaskListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAnimalBreed(CreatureSpawnEvent event) {
        if (DEBUG_MODE) plugin.getServer().broadcastMessage("Creature spawned!");
        Entity target = event.getEntity();
        if (target instanceof Animals) {
            if (DEBUG_MODE) plugin.getServer().broadcastMessage("It was an animal!");
            Animals animal = (Animals) target;
            if (event.getSpawnReason().equals(SpawnReason.BREEDING)) {
                if (DEBUG_MODE) plugin.getServer().broadcastMessage("It was bred!");
                List<Entity> nearbyEntities = animal.getNearbyEntities(2, 2, 2);
                Iterator<Entity> nearbyEntityIterator = nearbyEntities.iterator();

                while (nearbyEntityIterator.hasNext()) {
                    if (DEBUG_MODE) plugin.getServer().broadcastMessage("There was stuff near it!");
                    Entity nextEntity = nearbyEntityIterator.next();
                    if (nextEntity.getType().equals(EntityType.EXPERIENCE_ORB)) {
                        if (DEBUG_MODE) plugin.getServer().broadcastMessage("Including orbs!");
                        nextEntity.remove();
                    }
                }
            }
        }
    }
}
