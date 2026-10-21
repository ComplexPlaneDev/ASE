/** Concrete MEMENTO for {@link GameSession}: an immutable snapshot of the whole game state. */
public final class GameMemento implements Memento {

    private final String playerName;
    private final int health;
    private final int x;
    private final int y;
    private final int score;
    private final long timestamp;

    GameMemento(String playerName, int health, int x, int y, int score) {
        this.playerName = playerName;
        this.health = health;
        this.x = x;
        this.y = y;
        this.score = score;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    // Package-private: only GameSession (same package) can read the state back.
    String getPlayerName() { return playerName; }
    int getHealth() { return health; }
    int getX() { return x; }
    int getY() { return y; }
    int getScore() { return score; }
}
