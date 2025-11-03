public class MedievalMage implements Character {
    @Override public void attack() { System.out.println("Medieval Mage attacks!"); }
    @Override public void defend() { System.out.println("Medieval Mage defends!"); }
    @Override public String getDescription() { return "Medieval Mage"; }
}
