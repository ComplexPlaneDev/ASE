/** Game with named save slots and auto-save. */
public final class GameSaveDemo {

    public static void main(String[] args) {
        System.out.println("=== Example 2: Game save / load (named slots) ===");

        GameSession game = new GameSession("Aria");
        SaveManager saves = new SaveManager();

        // Play a bit, then auto-save.
        game.move(10, 5);
        game.addScore(50);
        saves.save(game.createMemento());
        System.out.println("auto-save after first level: " + game.describe());

        // Continue playing; state changes.
        game.takeDamage(40);
        game.move(-3, 8);
        game.addScore(120);
        System.out.println("after second level:          " + game.describe());

        // Manually save to a named slot.
        saves.saveToSlot("slot-1", game.createMemento());
        System.out.println("saved to slot-1");

        // Keep playing until the character dies.
        game.takeDamage(60);
        System.out.println("after boss fight:            " + game.describe());

        // Load the named slot.
        Memento m = saves.loadFromSlot("slot-1");
        if (m != null) {
            game.restore(m);
            System.out.println("loaded slot-1:               " + game.describe());
        }

        // Or fall back to the auto-save.
        game.takeDamage(100);
        Memento auto = saves.undo();
        if (auto != null) {
            game.restore(auto);
            System.out.println("restored last auto-save:     " + game.describe());
        }

        System.out.println("available slots: " + saves.availableSlots());
    }
}
