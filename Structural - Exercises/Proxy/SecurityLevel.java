
public enum SecurityLevel {
    PUBLIC(1),
    INTERNAL(2),
    CONFIDENTIAL(3),
    SECRET(4);

    private final int level;

    SecurityLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}


