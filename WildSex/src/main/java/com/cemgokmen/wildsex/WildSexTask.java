package com.cemgokmen.wildsex;

import com.cemgokmen.wildsex.api.WildAnimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class WildSexTask extends BukkitRunnable {

    private static final int MATING_DISTANCE = 14;
    private final WildSex plugin;
    private final Random randomizer;

    public WildSexTask(WildSex plugin) {
        this.plugin = plugin;
        this.randomizer = new Random();
    }

    public void run() {
        List<World> worlds = plugin.getServer().getWorlds();
        Iterator<World> worldIterator = worlds.iterator();

        while (worldIterator.hasNext()) {
            World world = worldIterator.next();

            Chunk[] chunks = world.getLoadedChunks();

            for (Chunk chunk : chunks) {
                Entity[] entities = chunk.getEntities(); //world.getEntitiesByClass(Animals.class);

                for (Entity e: entities) {
                    if (e instanceof Animals) {
                        Animals animal = (Animals) e;

                        if (animal.isAdult() && animal.canBreed() && !plugin.getWildAnimalHandler().isInLoveMode(animal)) {
                            double outcome = randomizer.nextDouble();
                            if (outcome <= plugin.getChance() && outcome <= getReproductionProbability(animal)) { // Not sure about this either
                                if (plugin.getMateMode()) {
                                    List<Entity> others = animal.getNearbyEntities(MATING_DISTANCE / 2, MATING_DISTANCE / 2, MATING_DISTANCE / 2);
                                    List<Animals> eligibleMates = new ArrayList<Animals>();

                                    Iterator<Entity> othersIterator = others.iterator();
                                    while (othersIterator.hasNext()) {
                                        Entity mate = othersIterator.next();

                                        if (mate.getClass() == animal.getClass()) {
                                            Animals mateAnimal = (Animals) mate;

                                            if (mateAnimal.isAdult() && mateAnimal.canBreed() && !plugin.getWildAnimalHandler().isInLoveMode(mateAnimal)) {
                                                eligibleMates.add(mateAnimal);
                                            }
                                        }
                                    }

                                    if (!eligibleMates.isEmpty()) {
                                        Animals mateAnimal = eligibleMates.get(randomizer.nextInt(eligibleMates.size()));
                                        plugin.getWildAnimalHandler().startLoveMode(animal);
                                        plugin.getWildAnimalHandler().startLoveMode(mateAnimal);
                                    }
                                } else {
                                    plugin.getWildAnimalHandler().startLoveMode(animal);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public double getReproductionProbability(Animals animal) {
        double scale = plugin.getMaxAnimalsCheckRadius() / 2.0;
        List<Entity> neighbors = animal.getNearbyEntities(scale, scale, scale);

        Iterator<Entity> neighborIterator = neighbors.iterator();
        int count = 0;

        while (neighborIterator.hasNext()) {
            Entity neighbor = neighborIterator.next();

            if (neighbor instanceof Animals)
                count++;
        }

        // If the count stays the same with a smaller area, then we should ideally be doing this with that smaller area.
        double radius = plugin.getMaxAnimalsCheckRadius() / 2.0;
        while (true) {
            double r = radius - 0.5;
            List<Entity> ns = animal.getNearbyEntities(r, r, r);

            Iterator<Entity> nI = ns.iterator();
            int c = 0;

            while (nI.hasNext()) {
                Entity n = nI.next();

                if (n instanceof Animals)
                    c++;
            }

            if (c == count) {
                radius = r;
            } else {
                break;
            }
        }

        // I'm really not sure about this formula.
        double targetPopulationForCheckRadius = plugin.getMaxAnimalsPerBlock() * Math.pow(radius * 2, 2); // Check for the square area, but apply it to the cube.
        double currentPopulationRatio = count / targetPopulationForCheckRadius;
        double probability = 1 - currentPopulationRatio;

        //plugin.getServer().broadcastMessage("Found " + count + " animals, so the probability is " + String.format("%%%.2f", probability * 100) + ".");

        return (probability > 0) ? probability : 0.0;
    }
}
