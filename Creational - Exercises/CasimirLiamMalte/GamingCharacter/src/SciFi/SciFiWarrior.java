package SciFi;

import Character.CharacterClass;

public class SciFiWarrior implements CharacterClass {
    @Override
    public void attack() {
        System.out.println("Laser shot!");
    }
    @Override
    public void defend() {
        System.out.println("Energy shield!");
    }
    @Override
    public void getDescription() {
        System.out.println("Sci-Fi Warrior, with laser rifle and energy shield!");
    }
}
