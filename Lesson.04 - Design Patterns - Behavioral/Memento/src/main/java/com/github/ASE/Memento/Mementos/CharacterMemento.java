package com.github.ASE.Memento.Mementos;

public class CharacterMemento {
    private final int health;
    private final int level;
    private final String inventory;
    private final String location;

    public CharacterMemento(int health, int level, String inventory, String location) {
        this.health = health;
        this.level = level;
        this.inventory = inventory;
        this.location = location;
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public String getInventory() {
        return inventory;
    }

    public String getLocation() {
        return location;
    }
}
