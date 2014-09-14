package com.cemgokmen.WildSex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_7_R3.EntityAnimal;
import org.bukkit.craftbukkit.v1_7_R3.entity.*;
import net.minecraft.server.v1_7_R3.EntityHuman;

import org.bukkit.World;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WildSexTask extends BukkitRunnable {
	private static final int MATING_DISTANCE = 14;
	
	private final JavaPlugin plugin;
	private final Random randomizer;
	private boolean mateMode;
	private double chance;
	
	public WildSexTask(JavaPlugin plugin, double chance, boolean mateMode) {
		this.plugin = plugin;
		this.randomizer = new Random();
		this.chance = chance;
		this.mateMode = mateMode;
	}
	
	public void run() {
		//plugin.getServer().broadcastMessage("Starting Wild Sex Sequence!");
		
		List<World> worlds = plugin.getServer().getWorlds();
		Iterator<World> worldIterator = worlds.iterator();
		
		//int cowsBred = 0;
		
		while(worldIterator.hasNext()) {
			World world = worldIterator.next();
			//plugin.getServer().broadcastMessage("Starting world " + world.getName() + ".");
			Collection<Cow> cows = world.getEntitiesByClass(Cow.class);

			Iterator<Cow> cowIterator = cows.iterator();
			while(cowIterator.hasNext()) {
				Cow cow = (Cow) cowIterator.next();
				//plugin.getServer().broadcastMessage("Starting cow.");
				EntityAnimal entity = (EntityAnimal) ((CraftEntity)((CraftAnimals)cow)).getHandle();
				
				if (cow.isAdult() && cow.canBreed() && !entity.ce()) {
					if (Math.random() <= this.chance) {
						if (this.mateMode) {
							List<Entity> others = cow.getNearbyEntities(MATING_DISTANCE / 2, MATING_DISTANCE / 2, MATING_DISTANCE / 2);
							List<Cow> eligibleCows = new ArrayList<Cow>();
							
							Iterator<Entity> othersIterator = others.iterator();
							while (othersIterator.hasNext()) {
								Entity other = othersIterator.next();
								
								if (other.getType() == EntityType.COW) {
									Cow otherCow = (Cow) other;
									EntityAnimal otherCowEntity = (EntityAnimal) ((CraftEntity)((CraftAnimals)otherCow)).getHandle();
									
									if (otherCow.isAdult() && otherCow.canBreed() && !otherCowEntity.ce()) {
										eligibleCows.add(otherCow);
									}
								}
							}
							
							//plugin.getServer().broadcastMessage("Found " + eligibleCows.size() + " eligible cows.");
							
							if (!eligibleCows.isEmpty()) {
								Cow mateCow = eligibleCows.get(randomizer.nextInt(eligibleCows.size()));
								EntityAnimal mateCowEntity = (EntityAnimal) ((CraftEntity)((CraftAnimals)mateCow)).getHandle();
								
								EntityHuman feeder = null;
								entity.f(feeder);
								mateCowEntity.f(feeder);
								
								//cowsBred++;
								//plugin.getServer().broadcastMessage("A new cow couple has been created!");
							}
						} else {
							EntityHuman feeder = null;
							entity.f(feeder);
						}
					}
				}
			}
		}
		
		//plugin.getServer().broadcastMessage("Wild Sex ended: " + cowsBred + " cows were mated.");
	}
}
