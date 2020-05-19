package com.cemgokmen.wildsex.wildanimal.v1_12_R1;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftAnimals;
import net.minecraft.server.v1_12_R1.EntityAnimal;
import java.util.UUID;

import org.bukkit.entity.Animals;

import com.cemgokmen.wildsex.api.WildAnimal;

import java.lang.reflect.Field;

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
        UUID uuid = null;

        try {
            Class<?> c = EntityAnimal.class;

            Field bx = c.getDeclaredField("bx");
            bx.setAccessible(true);
            bx.setInt(entity, WildAnimal.LOVEMODE_TICK_DURATION);

            Field by = c.getDeclaredField("by");
            by.setAccessible(true);
            by.set(entity, uuid);

            entity.breedItem = null;
            entity.world.broadcastEntityEffect(entity, (byte)18);
        } catch (Exception x) {
            throw new RuntimeException(x.toString());
        }
    }
}
