package com.github.ASE.Memento.Originators;

import com.github.ASE.Memento.Mementos.CharacterMemento;

public class GameCharacter {
    private int health;
    private int level;
    private String inventory;
    private String location;

    public GameCharacter(int health, int level, String inventory, String location) {
        this.health = health;
        this.level = level;
        this.inventory = inventory;
        this.location = location;
    }

    public void takeDamage(int damage) {
        System.out.println("Taking " + damage + " damage!");
        this.health -= damage;
    }

    public void gainExperience(int xp) {
        System.out.println("Gained " + xp + " XP!");
        this.level += xp / 100;
    }

    public void updateInventory(String newItem) {
        System.out.println("Picked up: " + newItem);
        this.inventory += ", " + newItem;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CharacterMemento saveToMemento() {
        System.out.println("Saving character state...");
        return new CharacterMemento(health, level, inventory, location);
    }

    public void restoreFromMemento(CharacterMemento memento) {
        this.health = memento.getHealth();
        this.level = memento.getLevel();
        this.inventory = memento.getInventory();
        this.location = memento.getLocation();
        System.out.println("Character reverted to: " + memento.getHealth() + " HP, Level " + memento.getLevel());
    }

    public void displayStatus() {
        System.out.println("Health: " + health + " | Level: " + level);
        System.out.println("Location: " + location);
        System.out.println("Inventory: " + inventory);
    }
}
