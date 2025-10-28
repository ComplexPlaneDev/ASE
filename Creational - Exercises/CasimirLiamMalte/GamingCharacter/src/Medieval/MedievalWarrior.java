package Medieval;

import Character.CharacterClass;

public class MedievalWarrior implements CharacterClass {

    @Override
    public void attack() { System.out.println("Hammer smash!"); }
    @Override
    public void defend() { System.out.println("Shield block!"); }
    @Override
    public void getDescription() { System.out.println("Medieval Warrior, with Hammer and Shield!"); }
}
