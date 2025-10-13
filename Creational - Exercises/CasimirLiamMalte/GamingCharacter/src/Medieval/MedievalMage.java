package Medieval;

import Character.CharacterClass;

public class MedievalMage implements CharacterClass {

    @Override
    public void attack() {
        System.out.println("Fireball!");
    }
    @Override
    public void defend() {
        System.out.println("Wall of Fire!");
    }
    @Override
    public void getDescription() {
        System.out.println("Medieval Mage, with fire spells!");
    }
}
