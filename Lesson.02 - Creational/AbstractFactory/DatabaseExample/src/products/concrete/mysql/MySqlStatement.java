package src.products.concrete.mysql;
import src.products.DbStatement;

/** CONCRETE PRODUCT B1 (concrete class). */
public class MySqlStatement implements DbStatement {
    @Override
    public void execute(String sql) {
        System.out.println("  [MySQL]      executed: " + sql);
    }
}
