package com.cemgokmen.wildsex;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class WildSexTaskListener implements Listener {

    private WildSex plugin;

    public WildSexTaskListener(WildSex plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityBreedEvent(EntityBreedEvent e) {
        LivingEntity father = e.getFather(), mother = e.getMother();
        if (plugin.isMatedAnimal(father) || plugin.isMatedAnimal(mother)) {
            e.setExperience(0);
            this.plugin.removeMatedAnimal(father);
            this.plugin.removeMatedAnimal(mother);
        }
    }

}
