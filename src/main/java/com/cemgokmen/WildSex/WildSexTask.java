package com.cemgokmen.WildSex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_7_R3.EntityAnimal;
import net.minecraft.server.v1_7_R3.EntityHuman;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R3.entity.*;
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
    private WildSexTaskListener theTaskListener;

    public WildSexTask(JavaPlugin plugin, double chance, boolean mateMode, WildSexTaskListener listener) {
        this.plugin = plugin;
        this.randomizer = new Random();
        this.chance = chance;
        this.mateMode = mateMode;
        this.theTaskListener = listener;
    }

    public void run() {
        this.theTaskListener.clear();
        List<World> worlds = plugin.getServer().getWorlds();
        Iterator<World> worldIterator = worlds.iterator();

        while (worldIterator.hasNext()) {
            World world = worldIterator.next();
            Collection<Animals> animals = world.getEntitiesByClass(Animals.class);

            Iterator<Animals> animalIterator = animals.iterator();
            while (animalIterator.hasNext()) {
                Animals animal = (Animals) animalIterator.next();
                EntityAnimal entity = (EntityAnimal) ((CraftEntity) ((CraftAnimals) animal)).getHandle();

                if (animal.isAdult() && animal.canBreed() && !entity.ce()) {
                    if (Math.random() <= this.chance) {
                        if (this.mateMode) {
                            List<Entity> others = animal.getNearbyEntities(MATING_DISTANCE / 2, MATING_DISTANCE / 2, MATING_DISTANCE / 2);
                            List<Animals> eligibleMates = new ArrayList<Animals>();

                            Iterator<Entity> othersIterator = others.iterator();
                            while (othersIterator.hasNext()) {
                                Entity mate = othersIterator.next();

                                if (mate instanceof Animals) {
                                    Animals mateAnimal = (Animals) mate;
                                    EntityAnimal mateAnimalEntity = (EntityAnimal) ((CraftEntity) ((CraftAnimals) mateAnimal)).getHandle();

                                    if (mateAnimal.isAdult() && mateAnimal.canBreed() && !mateAnimalEntity.ce()) {
                                        eligibleMates.add(mateAnimal);
                                    }
                                }
                            }

                            if (!eligibleMates.isEmpty()) {
                                Animals mateAnimal = eligibleMates.get(randomizer.nextInt(eligibleMates.size()));
                                EntityAnimal mateAnimalEntity = (EntityAnimal) ((CraftEntity) ((CraftAnimals) mateAnimal)).getHandle();

                                EntityHuman feeder = null;
                                entity.f(feeder);
                                mateAnimalEntity.f(feeder);
                            }
                        } else {
                            EntityHuman feeder = null;
                            entity.f(feeder);
                        }
                    }
                }
            }
        }
    }
}
