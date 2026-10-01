package src;
import src.products.DbConnection;
import src.products.DbStatement;
import src.products.factories.DatabaseFactory;
import src.products.factories.concrete.MySqlFactory;
import src.products.factories.concrete.PostgresFactory;

/**
 * The client runs the same query against two different databases by swapping
 * only the factory. Connection and statement always come from the same vendor,
 * so they are guaranteed to be compatible with each other.
 */
public class DatabaseDemo {
    public static void main(String[] args) {
        run(new MySqlFactory());
        System.out.println();
        run(new PostgresFactory());
    }

    private static void run(DatabaseFactory factory) {
        DbConnection conn = factory.createConnection();
        DbStatement stmt = factory.createStatement();
        conn.connect();
        stmt.execute("SELECT * FROM users WHERE active = true");
        conn.disconnect();
    }
}
