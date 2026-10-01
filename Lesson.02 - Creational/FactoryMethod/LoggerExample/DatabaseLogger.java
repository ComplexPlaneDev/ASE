/** Concrete Product C. */
public class DatabaseLogger implements Logger {

    private final String jdbcUrl;

    public DatabaseLogger(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    @Override
    public void log(String message) {
        System.out.println("DatabaseLogger(" + jdbcUrl + "): INSERT INTO logs VALUES (\'" + message + "\' )");
    }

    @Override
    public void close() {
        System.out.println("DatabaseLogger(" + jdbcUrl + "): connection released");
    }
}
