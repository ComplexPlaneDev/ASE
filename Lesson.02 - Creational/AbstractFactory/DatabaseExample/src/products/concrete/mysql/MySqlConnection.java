package src.products.concrete.mysql;
import src.products.DbConnection;

/** CONCRETE PRODUCT A1 (concrete class). */
public class MySqlConnection implements DbConnection {
    @Override
    public void connect() {
        System.out.println("  [MySQL]      connected to jdbc:mysql://localhost:3306/appdb");
    }
    @Override
    public void disconnect() {
        System.out.println("  [MySQL]      connection closed");
    }
}
