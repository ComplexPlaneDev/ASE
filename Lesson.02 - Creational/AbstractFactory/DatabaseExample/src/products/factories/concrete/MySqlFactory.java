package src.products.factories.concrete;
import src.products.DbConnection;
import src.products.DbStatement;
import src.products.concrete.mysql.MySqlConnection;
import src.products.concrete.mysql.MySqlStatement;
import src.products.factories.DatabaseFactory;

/** CONCRETE FACTORY (concrete class) — the MySQL family. */
public class MySqlFactory extends DatabaseFactory {
    @Override
    public DbConnection createConnection() { return new MySqlConnection(); }
    @Override
    public DbStatement createStatement()   { return new MySqlStatement(); }
}
