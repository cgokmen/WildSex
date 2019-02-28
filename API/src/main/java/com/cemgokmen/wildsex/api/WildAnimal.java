package com.cemgokmen.wildsex.api;

import org.bukkit.entity.Animals;

public interface WildAnimal {

    public static final int LOVEMODE_TICK_DURATION = 600; // The equivalent of feeding an entity an item

    public boolean isInLoveMode(Animals animal);

    public void startLoveMode(Animals animal);

}
