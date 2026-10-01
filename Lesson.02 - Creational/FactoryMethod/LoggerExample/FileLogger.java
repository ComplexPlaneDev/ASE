/** Concrete Product A. */
public class FileLogger implements Logger {

    private final String fileName;

    public FileLogger(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void log(String message) {
        System.out.println("FileLogger(" + fileName + "): " + message);
    }

    @Override
    public void close() {
        System.out.println("FileLogger(" + fileName + "): stream closed");
    }
}
