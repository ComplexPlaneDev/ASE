public class FileLoggerCreator extends LoggerCreator {
    private String path;

    public FileLoggerCreator(String path) {
        this.path = path;
    }

    @Override
    public Logger createLogger() {
        return new FileLogger(path);
    }
}