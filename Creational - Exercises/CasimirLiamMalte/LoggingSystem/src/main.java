public class Main {
    public static void main(String[] args) {
        Logger dbLogger = new DatabaseLogger();
        Logger consoleLogger = new ConsoleLogger();

        LogMessage msg1 = new SimpleLogMessage("info", "System started");
        LogMessage msg2 = new SimpleLogMessage("error", "Something failed");

        dbLogger.log(msg1);
        consoleLogger.log(msg2);
    }
}