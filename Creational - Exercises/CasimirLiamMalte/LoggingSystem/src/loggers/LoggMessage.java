import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLogMessage implements LogMessage {
    private final String level;
    private final String message;
    private final String timestamp;

    public SimpleLogMessage(String level, String message) {
        this.level = level.toUpperCase();
        this.message = message;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] [" + level + "] " + message;
    }
}