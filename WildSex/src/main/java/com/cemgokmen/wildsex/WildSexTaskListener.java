package com.cemgokmen.wildsex;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import java.util.Set;

public class WildSexTaskListener implements Listener {
    private WildSex plugin;

    public WildSexTaskListener(WildSex plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityBreedEvent(EntityBreedEvent e) {
        Set<Entity> matedAnimals = plugin.lastMateAnimals;
        if (matedAnimals.contains(e.getFather()) || matedAnimals.contains(e.getMother())) {
            e.setExperience(0);
            matedAnimals.remove(e.getFather());
            matedAnimals.remove(e.getMother());
        }
    }
}