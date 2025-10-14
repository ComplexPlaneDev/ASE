package SciFi;

import Character.CharacterClass;
import Character.CharacterFactory;

public class SciFiFactory implements CharacterFactory {
    @Override
    public CharacterClass createWarrior() {
        return new SciFiWarrior();
    }
    @Override
    public CharacterClass createMage() {
        return new SciFiMage();
    }
}
