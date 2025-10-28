public class ConsoleLogger implements Logger {
    @Override
    public void log(LogMessage message) {
        System.out.println(message.toString());
    }
}