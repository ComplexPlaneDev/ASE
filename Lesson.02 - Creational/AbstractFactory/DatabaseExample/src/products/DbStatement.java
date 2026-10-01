package src.products;
/** PRODUCT B (interface) — an SQL statement bound to a connection. */
public interface DbStatement {
    void execute(String sql);
}
