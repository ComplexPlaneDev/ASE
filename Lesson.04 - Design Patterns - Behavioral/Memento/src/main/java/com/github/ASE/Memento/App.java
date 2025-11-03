package com.github.ASE.Memento;

import com.github.ASE.Memento.CareTakers.GameHistory;
import com.github.ASE.Memento.Originators.GameCharacter;

public class App {

    public static void main(String[] args) {
        GameCharacter character = new GameCharacter(100, 1, "Sword", "Forest");
        GameHistory history = new GameHistory();

        // Initial state
        System.out.println("=== Starting Adventure ===");
        character.displayStatus();

        // Save initial state
        history.saveState(character);

        // First battle
        System.out.println("\n=== First Battle ===");
        character.takeDamage(20);
        character.gainExperience(150);
        character.updateInventory("Potion");
        character.setLocation("Cave");
        character.displayStatus();
        history.saveState(character);

        // Second battle (riskier)
        System.out.println("\n=== Boss Battle ===");
        character.takeDamage(50);
        character.updateInventory("Legendary Armor");
        character.setLocation("Dungeon");
        character.displayStatus();

        // Save again
        history.saveState(character);

        // Revert to previous state
        System.out.println("\n=== Reverting to Last Save ===");
        history.undo(character);
        character.displayStatus();

        // Redo
        System.out.println("\n=== Redo to Final State ===");
        history.redo(character);
        character.displayStatus();

        // View full history
        history.showHistory();
    }
}
