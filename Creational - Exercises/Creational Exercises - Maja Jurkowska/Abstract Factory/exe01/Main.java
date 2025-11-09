public class Main {
    public static void main(String[] args) {
        boolean sciFi = true; // can flip to false to have Medieval

        // getting the singleton instance
        ThemeContext ctx = ThemeContext.getInstance();

        // setting the factory once
        ctx.setFactory(sciFi ? new SciFiFactory() : new MedievalFactory());

        // getting the factory from the singleton
        CharacterFactory factory = ctx.getFactory();

        // creating own themed characters
        Character warrior = factory.createWarrior();
        Character mage = factory.createMage();

        // displaying results
        System.out.println("Team: " + warrior.getDescription() + " + " + mage.getDescription());
        warrior.attack(); warrior.defend();
        mage.attack(); mage.defend();
    }
}
