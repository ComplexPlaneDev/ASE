import Character.CharacterFactory;
import Character.CharacterClass;
import Medieval.MedievalFactory;
import SciFi.SciFiFactory;

public class Main {
    public static void main(String[] args) {
        CharacterFactory medievalCharacterFactory = new MedievalFactory();
        CharacterClass medievalWarrior = medievalCharacterFactory.createWarrior();
        CharacterClass medievalMage = medievalCharacterFactory.createMage();

        medievalWarrior.getDescription();
        medievalWarrior.attack();
        medievalWarrior.defend();

        medievalMage.getDescription();
        medievalMage.attack();
        medievalMage.defend();

        CharacterFactory scifiCharacterFactory = new SciFiFactory();
        CharacterClass sciFiWarrior = scifiCharacterFactory.createWarrior();
        CharacterClass sciFiMage = scifiCharacterFactory.createMage();

        sciFiWarrior.getDescription();
        sciFiWarrior.attack();
        sciFiWarrior.defend();

        sciFiMage.getDescription();
        sciFiMage.attack();
        sciFiMage.defend();
    }
}