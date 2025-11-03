public class SciFiFactory implements CharacterFactory {
    @Override public Character createWarrior() { return new SciFiWarrior(); }
    @Override public Character createMage() { return new SciFiMage(); }
}
