public abstract class LoggerCreator {
    public abstract Logger createLogger();

    public void logMessage(String level, String message) {
        Logger logger = createLogger();
        logger.log(level, message);
    }
}