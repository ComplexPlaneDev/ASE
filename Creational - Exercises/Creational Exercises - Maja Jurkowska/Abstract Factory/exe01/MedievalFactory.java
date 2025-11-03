public class MedievalFactory implements CharacterFactory {
    @Override public Character createWarrior() { return new MedievalWarrior(); }
    @Override public Character createMage() { return new MedievalMage(); }
}
