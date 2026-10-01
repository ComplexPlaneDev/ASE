package src.products.concrete.pgsql;
import src.products.DbStatement;

/** CONCRETE PRODUCT B2 (concrete class). */
public class PostgresStatement implements DbStatement {
    @Override
    public void execute(String sql) {
        System.out.println("  [PostgreSQL] executed: " + sql);
    }
}
