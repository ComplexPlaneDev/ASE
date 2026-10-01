package src.products.factories.concrete;
import src.products.DbConnection;
import src.products.DbStatement;
import src.products.concrete.pgsql.PostgresConnection;
import src.products.concrete.pgsql.PostgresStatement;
import src.products.factories.DatabaseFactory;

/** CONCRETE FACTORY (concrete class) — the PostgreSQL family. */
public class PostgresFactory extends DatabaseFactory {
    @Override
    public DbConnection createConnection() { return new PostgresConnection(); }
    @Override
    public DbStatement createStatement()   { return new PostgresStatement(); }
}
