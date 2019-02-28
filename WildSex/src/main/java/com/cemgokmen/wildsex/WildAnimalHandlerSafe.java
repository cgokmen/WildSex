package com.cemgokmen.wildsex;

import com.cemgokmen.wildsex.api.WildAnimal;

import org.bukkit.entity.Animals;

// An API-safe WildAnimal implementation (added in 1.13.2+). Should be future-proof to avoid NMS as best as possible
public class WildAnimalHandlerSafe implements WildAnimal {

    @Override
    public boolean isInLoveMode(Animals animal) {
        return animal.isLoveMode();
    }

    @Override
    public void startLoveMode(Animals animal) {
        animal.setLoveModeTicks(WildAnimal.LOVEMODE_TICK_DURATION);
    }

}
