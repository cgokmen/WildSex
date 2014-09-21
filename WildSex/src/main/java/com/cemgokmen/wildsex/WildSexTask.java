package com.cemgokmen.wildsex;

import com.cemgokmen.wildsex.api.WildAnimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WildSexTask extends BukkitRunnable {

    private static final int MATING_DISTANCE = 14;
    private final JavaPlugin plugin;
    private final Random randomizer;
    private boolean mateMode;
    private double chance;
    private WildAnimal wildAnimalHandler;

    public WildSexTask(JavaPlugin plugin, WildAnimal wildAnimalHandler, double chance, boolean mateMode) {
        this.plugin = plugin;
        this.randomizer = new Random();
        this.chance = chance;
        this.mateMode = mateMode;
        this.wildAnimalHandler = wildAnimalHandler;
    }

    public void run() {
        List<World> worlds = plugin.getServer().getWorlds();
        Iterator<World> worldIterator = worlds.iterator();

        while (worldIterator.hasNext()) {
            World world = worldIterator.next();
            Collection<Animals> animals = world.getEntitiesByClass(Animals.class);

            Iterator<Animals> animalIterator = animals.iterator();
            while (animalIterator.hasNext()) {
                Animals animal = (Animals) animalIterator.next();

                if (animal.isAdult() && animal.canBreed() && !wildAnimalHandler.isInLoveMode(animal)) {
                    if (Math.random() <= this.chance) {
                        if (this.mateMode) {
                            List<Entity> others = animal.getNearbyEntities(MATING_DISTANCE / 2, MATING_DISTANCE / 2, MATING_DISTANCE / 2);
                            List<Animals> eligibleMates = new ArrayList<Animals>();

                            Iterator<Entity> othersIterator = others.iterator();
                            while (othersIterator.hasNext()) {
                                Entity mate = othersIterator.next();

                                if (mate.getClass() == animal.getClass()) {
                                    Animals mateAnimal = (Animals) mate;

                                    if (mateAnimal.isAdult() && mateAnimal.canBreed() && !wildAnimalHandler.isInLoveMode(mateAnimal)) {
                                        eligibleMates.add(mateAnimal);
                                    }
                                }
                            }

                            if (!eligibleMates.isEmpty()) {
                                Animals mateAnimal = eligibleMates.get(randomizer.nextInt(eligibleMates.size()));
                                wildAnimalHandler.startLoveMode(animal);
                                wildAnimalHandler.startLoveMode(mateAnimal);
                            }
                        } else {
                            wildAnimalHandler.startLoveMode(animal);
                        }
                    }
                }
            }
        }
    }
}
