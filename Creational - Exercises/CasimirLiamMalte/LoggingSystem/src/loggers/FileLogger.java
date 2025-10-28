import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger {
    private final String filePath;

    public FileLogger(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void log(LogMessage message) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}