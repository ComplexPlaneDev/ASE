public class SciFiWarrior implements Character {
    @Override public void attack() { System.out.println("Sci-Fi Warrior attacks!"); }
    @Override public void defend() { System.out.println("Sci-Fi Warrior defends!"); }
    @Override public String getDescription() { return "Sci-Fi Warrior"; }
}
