import java.sql.*;

public class DatabaseLogger implements Logger {
    private static final String DB_URL = "jdbc:sqlite:logs.db";
    private Connection conn;

    public DatabaseLogger() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS logs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                timestamp TEXT,
                level TEXT,
                message TEXT
            );
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void log(LogMessage message) {
        String insert = "INSERT INTO logs (timestamp, level, message) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insert)) {
            pstmt.setString(1, message.getTimestamp());
            pstmt.setString(2, message.getLevel());
            pstmt.setString(3, message.getMessage());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}