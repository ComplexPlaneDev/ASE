package src.products.factories;
import src.products.DbConnection;
import src.products.DbStatement;

/**
 * ABSTRACT FACTORY (abstract class)
 * One creation method per database role. The application code depends only on
 * this type, so it can be pointed at any supported database.
 */
public abstract class DatabaseFactory {
    public abstract DbConnection createConnection();
    public abstract DbStatement createStatement();
}
