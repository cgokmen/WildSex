package com.cemgokmen.wildsex;

import java.util.Iterator;
import java.util.List;

import com.bergerkiller.bukkit.common.events.EntityAddEvent;
import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WildSexTaskListener implements Listener {
    private static final double MAX_DISTANCE = 0.01;
    private WildSex plugin;

    public WildSexTaskListener(WildSex plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityAddEvent(EntityAddEvent event) {
        if (event.getEntityType() == EntityType.EXPERIENCE_ORB) {
            // Check if appeared within 0.5 meters of any of the auto-mated animals
            ExperienceOrb orb = (ExperienceOrb) event.getEntity();
            Location orbLocation = orb.getLocation();
            boolean isFromMatedAnimal = false;
            double dist = 0.0;
            for (Entity e : plugin.getMatedAnimals()) {
                Location l = e.getLocation();
                double distance = l.distance(orbLocation);
                if (distance < MAX_DISTANCE) {
                    isFromMatedAnimal = true;
                    dist = distance;
                    break;
                }
            }
            if (isFromMatedAnimal) {
                orb.remove();
                //plugin.getServer().broadcastMessage(String.format("Destroyed an orb %.2f meters away from a mated animal.", dist));
            }
        }
    }
}