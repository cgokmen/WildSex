package com.cemgokmen.wildsex.wildanimal.v1_7_R3;

import org.bukkit.craftbukkit.v1_7_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftAnimals;
import net.minecraft.server.v1_7_R3.EntityAnimal;
import net.minecraft.server.v1_7_R3.EntityHuman;

import org.bukkit.entity.Animals;

import com.cemgokmen.wildsex.api.WildAnimal;

public class WildAnimalHandler implements WildAnimal {
    private EntityAnimal getEntityAnimal(Animals animal) {
        EntityAnimal entity = (EntityAnimal) ((CraftEntity) ((CraftAnimals) animal)).getHandle();
        return entity;
    }

    public boolean isInLoveMode(Animals animal) {
        EntityAnimal entity = getEntityAnimal(animal);
        return entity.ce();
    }

    public void startLoveMode(Animals animal) {
        EntityAnimal entity = getEntityAnimal(animal);
        EntityHuman human = null;

        entity.f(human);
    }

    public void endLoveMode(Animals animal) {
        EntityAnimal entity = getEntityAnimal(animal);
        entity.cf();
    }
}
