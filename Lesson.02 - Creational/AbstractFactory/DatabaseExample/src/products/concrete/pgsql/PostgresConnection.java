package src.products.concrete.pgsql;
import src.products.DbConnection;

/** CONCRETE PRODUCT A2 (concrete class). */
public class PostgresConnection implements DbConnection {
    @Override
    public void connect() {
        System.out.println("  [PostgreSQL] connected to jdbc:postgresql://localhost:5432/appdb");
    }
    @Override
    public void disconnect() {
        System.out.println("  [PostgreSQL] connection closed");
    }
}
