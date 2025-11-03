package com.github.ASE.Memento.CareTakers;

import java.util.ArrayList;
import java.util.List;

import com.github.ASE.Memento.Mementos.CharacterMemento;
import com.github.ASE.Memento.Originators.GameCharacter;

public class GameHistory {
    private final List<CharacterMemento> history = new ArrayList<>();
    private int currentVersion = -1;

    public void saveState(GameCharacter character) {
        history.add(character.saveToMemento());
        currentVersion++;
        System.out.println("Saved to version " + (currentVersion + 1));
    }

    public void undo(GameCharacter character) {
        if (currentVersion > 0) {
            currentVersion--;
            CharacterMemento previousState = history.get(currentVersion);
            System.out.println("Undoing to version " + (currentVersion + 1));
            character.restoreFromMemento(previousState);
        } else {
            System.out.println("Cannot undo further.");
        }
    }

    public void redo(GameCharacter character) {
        if (currentVersion < history.size() - 1) {
            currentVersion++;
            CharacterMemento nextState = history.get(currentVersion);
            System.out.println("Redoing to version " + (currentVersion + 1));
            character.restoreFromMemento(nextState);
        } else {
            System.out.println("Cannot redo further.");
        }
    }

    public void showHistory() {
        System.out.println("Game Character History:");
        for (int i = 0; i < history.size(); i++) {
            CharacterMemento memento = history.get(i);
            System.out.println("Version " + (i + 1) + ": HP=" + memento.getHealth() + ", Level=" + memento.getLevel()
                    + ", Location=" + memento.getLocation());
        }
    }
}
