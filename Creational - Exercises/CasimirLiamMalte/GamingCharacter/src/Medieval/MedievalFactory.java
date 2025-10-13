package Medieval;

import Character.CharacterFactory;
import Character.CharacterClass;

public class MedievalFactory implements CharacterFactory {
    @Override
    public CharacterClass createWarrior() {
        return new MedievalWarrior();
    }
    @Override
    public CharacterClass createMage() {
        return new MedievalMage();
    }
}
