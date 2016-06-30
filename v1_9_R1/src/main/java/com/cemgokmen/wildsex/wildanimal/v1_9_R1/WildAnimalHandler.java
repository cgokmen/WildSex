package com.cemgokmen.wildsex.wildanimal.v1_9_R1;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftAnimals;
import net.minecraft.server.v1_9_R1.EntityAnimal;
import net.minecraft.server.v1_9_R1.EntityHuman;

import org.bukkit.entity.Animals;

import com.cemgokmen.wildsex.api.WildAnimal;

public class WildAnimalHandler implements WildAnimal {
private EntityAnimal getEntityAnimal(Animals animal) {
    EntityAnimal entity = (EntityAnimal) ((CraftEntity) ((CraftAnimals) animal)).getHandle();
    return entity;
}

@Override
public boolean isInLoveMode(Animals animal) {
    EntityAnimal entity = getEntityAnimal(animal);
    return entity.isInLove();
}

public void startLoveMode(Animals animal) {
    EntityAnimal entity = getEntityAnimal(animal);
    EntityHuman human = null;

    entity.c(human);
}

public void endLoveMode(Animals animal) {
    EntityAnimal entity = getEntityAnimal(animal);
    entity.resetLove();
}
}
