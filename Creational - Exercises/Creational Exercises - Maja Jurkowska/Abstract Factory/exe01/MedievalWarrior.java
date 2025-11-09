public class MedievalWarrior implements Character {
    @Override public void attack() { System.out.println("Medieval Warrior attacks!"); }
    @Override public void defend() { System.out.println("Medieval Warrior defends!"); }
    @Override public String getDescription() { return "Medieval Warrior"; }
}
