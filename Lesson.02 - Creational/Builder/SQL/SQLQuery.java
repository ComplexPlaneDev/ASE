import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Product: SQLQuery (IMMUTABLE)
 *
 * Represents a fully constructed SQL SELECT statement.
 * Once built, it cannot be modified — thread-safe by design.
 *
 * The only way to create an SQLQuery is through the fluent Builder.
 */
public class SQLQuery {

    private final List<String> columns;
    private final String table;
    private final List<String> joins;
    private final List<String> conditions;
    private final List<String> groupByColumns;
    private final String havingClause;
    private final List<String> orderByColumns;
    private final int limit;
    private final int offset;
    private final boolean distinct;

    private SQLQuery(Builder builder) {
        this.columns        = Collections.unmodifiableList(new ArrayList<>(builder.columns));
        this.table          = builder.table;
        this.joins          = Collections.unmodifiableList(new ArrayList<>(builder.joins));
        this.conditions     = Collections.unmodifiableList(new ArrayList<>(builder.conditions));
        this.groupByColumns = Collections.unmodifiableList(new ArrayList<>(builder.groupByColumns));
        this.havingClause   = builder.havingClause;
        this.orderByColumns = Collections.unmodifiableList(new ArrayList<>(builder.orderByColumns));
        this.limit          = builder.limit;
        this.offset         = builder.offset;
        this.distinct       = builder.distinct;
    }

    // --- Getters ---

    public List<String> getColumns()        { return columns; }
    public String getTable()                { return table; }
    public List<String> getJoins()          { return joins; }
    public List<String> getConditions()     { return conditions; }
    public List<String> getGroupByColumns() { return groupByColumns; }
    public String getHavingClause()         { return havingClause; }
    public List<String> getOrderByColumns() { return orderByColumns; }
    public int getLimit()                   { return limit; }
    public int getOffset()                  { return offset; }
    public boolean isDistinct()             { return distinct; }

    /**
     * Renders the final SQL string.
     */
    public String toSQL() {
        StringBuilder sql = new StringBuilder();

        // SELECT
        sql.append("SELECT ");
        if (distinct) sql.append("DISTINCT ");
        sql.append(columns.isEmpty() ? "*" : String.join(", ", columns));

        // FROM
        sql.append("\nFROM ").append(table);

        // JOINs
        for (String join : joins) {
            sql.append("\n").append(join);
        }

        // WHERE
        if (!conditions.isEmpty()) {
            sql.append("\nWHERE ").append(String.join("\n  AND ", conditions));
        }

        // GROUP BY
        if (!groupByColumns.isEmpty()) {
            sql.append("\nGROUP BY ").append(String.join(", ", groupByColumns));
        }

        // HAVING
        if (havingClause != null) {
            sql.append("\nHAVING ").append(havingClause);
        }

        // ORDER BY
        if (!orderByColumns.isEmpty()) {
            sql.append("\nORDER BY ").append(String.join(", ", orderByColumns));
        }

        // LIMIT / OFFSET
        if (limit > 0) {
            sql.append("\nLIMIT ").append(limit);
        }
        if (offset > 0) {
            sql.append(" OFFSET ").append(offset);
        }

        sql.append(";");
        return sql.toString();
    }

    @Override
    public String toString() {
        return toSQL();
    }

    // ═══════════════════════════════════════════════════════════════════
    //  FLUENT BUILDER
    // ═══════════════════════════════════════════════════════════════════

    public static class Builder {

        // Required
        private final String table;

        // Optional (with defaults)
        private List<String> columns        = new ArrayList<>();
        private List<String> joins          = new ArrayList<>();
        private List<String> conditions     = new ArrayList<>();
        private List<String> groupByColumns = new ArrayList<>();
        private String havingClause         = null;
        private List<String> orderByColumns = new ArrayList<>();
        private int limit                   = -1;
        private int offset                  = -1;
        private boolean distinct            = false;

        /**
         * Constructor takes the required FROM table.
         */
        public Builder(String table) {
            this.table = table;
        }

        // --- Column selection ---

        public Builder select(String... columns) {
            Collections.addAll(this.columns, columns);
            return this;
        }

        public Builder distinct() {
            this.distinct = true;
            return this;
        }

        // --- Joins ---

        public Builder innerJoin(String table, String onCondition) {
            this.joins.add("INNER JOIN " + table + " ON " + onCondition);
            return this;
        }

        public Builder leftJoin(String table, String onCondition) {
            this.joins.add("LEFT JOIN " + table + " ON " + onCondition);
            return this;
        }

        public Builder rightJoin(String table, String onCondition) {
            this.joins.add("RIGHT JOIN " + table + " ON " + onCondition);
            return this;
        }

        // --- Filtering ---

        public Builder where(String condition) {
            this.conditions.add(condition);
            return this;
        }

        public Builder whereEquals(String column, String value) {
            this.conditions.add(column + " = '" + value + "'");
            return this;
        }

        public Builder whereEquals(String column, int value) {
            this.conditions.add(column + " = " + value);
            return this;
        }

        public Builder whereGreaterThan(String column, int value) {
            this.conditions.add(column + " > " + value);
            return this;
        }

        public Builder whereLessThan(String column, int value) {
            this.conditions.add(column + " < " + value);
            return this;
        }

        public Builder whereLike(String column, String pattern) {
            this.conditions.add(column + " LIKE '" + pattern + "'");
            return this;
        }

        public Builder whereIn(String column, String... values) {
            StringBuilder in = new StringBuilder(column + " IN (");
            for (int i = 0; i < values.length; i++) {
                if (i > 0) in.append(", ");
                in.append("'").append(values[i]).append("'");
            }
            in.append(")");
            this.conditions.add(in.toString());
            return this;
        }

        public Builder whereNotNull(String column) {
            this.conditions.add(column + " IS NOT NULL");
            return this;
        }

        // --- Grouping ---

        public Builder groupBy(String... columns) {
            Collections.addAll(this.groupByColumns, columns);
            return this;
        }

        public Builder having(String condition) {
            this.havingClause = condition;
            return this;
        }

        // --- Ordering ---

        public Builder orderBy(String column) {
            this.orderByColumns.add(column + " ASC");
            return this;
        }

        public Builder orderByDesc(String column) {
            this.orderByColumns.add(column + " DESC");
            return this;
        }

        // --- Pagination ---

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        // --- Build ---

        /**
         * Validates and produces the immutable SQLQuery.
         */
        public SQLQuery build() {
            if (table == null || table.isBlank()) {
                throw new IllegalStateException("FROM table must not be blank.");
            }
            if (havingClause != null && groupByColumns.isEmpty()) {
                throw new IllegalStateException("HAVING requires a GROUP BY clause.");
            }
            if (offset > 0 && limit <= 0) {
                throw new IllegalStateException("OFFSET requires a LIMIT clause.");
            }
            return new SQLQuery(this);
        }
    }
}
