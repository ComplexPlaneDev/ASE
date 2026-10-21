/** Concrete ORIGINATOR: the state of a running game session. */
public final class GameSession implements Originator {

    private String playerName;
    private int health;
    private int x;
    private int y;
    private int score;

    public GameSession(String playerName) {
        this.playerName = playerName;
        this.health = 100;
        this.x = 0;
        this.y = 0;
        this.score = 0;
    }

    // ---- gameplay (mutates the state) ----

    public void takeDamage(int amount) {
        health = Math.max(0, health - amount);
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void addScore(int points) {
        score += points;
    }

    public String describe() {
        return playerName + " [hp=" + health + ", pos=(" + x + "," + y + "), score=" + score + "]";
    }

    // ---- Originator role ----

    @Override
    public Memento createMemento() {
        return new GameMemento(playerName, health, x, y, score);
    }

    @Override
    public void restore(Memento memento) {
        if (!(memento instanceof GameMemento)) {
            throw new IllegalArgumentException(
                    "Expected a GameMemento but got: " + memento.getClass().getName());
        }
        GameMemento gm = (GameMemento) memento;
        playerName = gm.getPlayerName();
        health = gm.getHealth();
        x = gm.getX();
        y = gm.getY();
        score = gm.getScore();
    }
}
