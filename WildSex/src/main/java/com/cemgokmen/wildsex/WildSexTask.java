package com.cemgokmen.wildsex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cemgokmen.wildsex.api.WildAnimal;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class WildSexTask extends BukkitRunnable {

    private static final List<Animals> ELIGIBLE_MATES_BUFFER = new ArrayList<>(64); // 64 is worst-case scenario

    private final WildSex plugin;
    private final Random random;

    public WildSexTask(WildSex plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    @Override
    public void run() {
        ELIGIBLE_MATES_BUFFER.clear();
        plugin.clearMatedAnimals();

        List<World> worlds = Bukkit.getWorlds();
        for (World world : worlds) {
            Chunk[] chunks = world.getLoadedChunks();

            for (Chunk chunk : chunks) {
                Entity[] entities = chunk.getEntities();

                for (Entity e : entities) {
                    if (!(e instanceof Animals)) continue;

                    Animals animal = (Animals) e;
                    if (!isCapableOfMating(animal)) continue;

                    double outcome = random.nextDouble();
                    if (outcome > getReproductionProbability(animal)) continue;

                    if (!plugin.getMateMode()) {
                        plugin.getWildAnimalHandler().startLoveMode(animal);
                        continue;
                    }

                    int mateDistance = plugin.getMaxMateDistance() / 2;
                    List<Entity> others = animal.getNearbyEntities(mateDistance, mateDistance, mateDistance);
                    List<Animals> eligibleMates = new ArrayList<>();

                    others.stream().filter(nearby -> nearby.getType() == animal.getType())
                        .map(nearby -> (Animals) nearby).filter(this::isCapableOfMating)
                        .forEach(ELIGIBLE_MATES_BUFFER::add);

                    if (!eligibleMates.isEmpty()) {
                        Animals mateAnimal = eligibleMates.get(random.nextInt(eligibleMates.size()));
                        WildAnimal animalHandler = plugin.getWildAnimalHandler();

                        animalHandler.startLoveMode(animal);
                        plugin.addMatedAnimal(animal);
                        animalHandler.startLoveMode(mateAnimal);
                        plugin.addMatedAnimal(mateAnimal);
                    }
                }
            }
        }
    }

    public double getReproductionProbability(Animals animal) {
        double radius = plugin.getMaxAnimalsCheckRadius() / 2.0;
        List<Entity> neighbors = animal.getNearbyEntities(radius, radius, radius);
        int count = (int) neighbors.stream().filter(e -> e instanceof Animals).count();

        // If the count stays the same with a smaller area, then we should ideally be doing this with that smaller area.
        /*if (count > 0) {
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
        }*/

        // I'm really not sure about this formula.
        double targetPopulationForCheckRadius = plugin.getMaxAnimalsPerBlock() * radius * radius * 4; // Check for the square area, but apply it to the cube.
        double currentPopulationRatio = count / targetPopulationForCheckRadius;
        double probability = plugin.getChance() * (1 - currentPopulationRatio);

        //plugin.getServer().broadcastMessage("Found " + count + " animals in a radius of " + ((int) (radius*2)) + ", so the probability is " + String.format("%%%.2f", probability * 100) + ".");

        return Math.max(probability, 0.0);
    }

    private boolean isCapableOfMating(Animals animal) {
        return animal.isAdult() && animal.canBreed() && !plugin.getWildAnimalHandler().isInLoveMode(animal);
    }

}
