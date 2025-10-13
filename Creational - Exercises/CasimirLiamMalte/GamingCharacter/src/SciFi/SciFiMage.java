package SciFi;

import Character.CharacterClass;

public class SciFiMage implements CharacterClass {
    @Override
    public void attack() {
        System.out.println("Quantum Burst!");
    }
    @Override
    public void defend() {
        System.out.println("Phase Shift!");
    }
    @Override
    public void getDescription() {
        System.out.println("Sci-Fi Mage, with Psi spells!");
    }
}
