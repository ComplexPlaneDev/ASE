import java.util.*;

/**
 * SQL injection happens when attacker-controlled DATA is concatenated into a query string
 * and reinterpreted as CODE. The fix is a PREPARED/PARAMETERIZED statement: the query
 * structure is fixed, and inputs are BOUND as values the engine never re-parses as SQL.
 *
 * To stay single-file and database-free, we include a TINY in-file "SQL engine" that only
 * understands  SELECT * FROM users WHERE name = '<literal>'  and the classic tautology
 * injection  ' OR '1'='1 . It is a teaching stub, not a real parser — but it is enough to
 * show how concatenation lets DATA change the query's MEANING, while binding does not.
 */
public class Main {

    // ---- a throwaway "users table" ----
    static final Map<String, String> USERS = new LinkedHashMap<>(Map.of(
            "alice", "alice@example.com",
            "bob",   "bob@example.com",
            "admin", "admin@example.com"));

    /**
     * A deliberately naive mini-engine. It extracts the string between  name = '  and the
     * next  '  ... which is exactly what makes concatenation dangerous: a tautology like
     * x' OR '1'='1  turns a single-row lookup into "match everything".
     */
    static List<String> runRawSql(String sql) {
        String marker = "WHERE name = '";
        int i = sql.indexOf(marker);
        if (i < 0) return List.of();
        String rest = sql.substring(i + marker.length());
        // Detect the tautology our stub "engine" (like a real one) would evaluate as always-true.
        if (rest.replace(" ", "").toUpperCase().contains("OR'1'='1"))
            return new ArrayList<>(USERS.keySet());         // INJECTION: every row leaks
        String name = rest.substring(0, Math.max(0, rest.indexOf('\'')));
        return USERS.containsKey(name) ? List.of(name) : List.of();
    }

    // VULNERABLE: builds the query by concatenating untrusted input.
    static List<String> findUserVulnerable(String nameInput) {
        String sql = "SELECT * FROM users WHERE name = '" + nameInput + "'";
        note("     built SQL: " + sql);
        return runRawSql(sql);
    }

    // SAFE: a parameterized query. The structure is fixed ("?"), the input is BOUND as a
    // value. A real driver (PreparedStatement.setString) never re-parses it as SQL, so the
    // tautology is searched for LITERALLY and matches nothing. We simulate that binding here.
    static List<String> findUserSafe(String nameInput) {
        String queryStructure = "SELECT * FROM users WHERE name = ?";   // fixed, compiled once
        note("     prepared : " + queryStructure + "   [1] = " + quote(nameInput));
        // Binding = exact, literal match. No string is ever interpreted as SQL.
        return USERS.containsKey(nameInput) ? List.of(nameInput) : List.of();
    }

    public static void main(String[] args) {
        String legit = "alice";
        String attack = "x' OR '1'='1";   // classic tautology injection

        section("VULNERABLE: concatenated query — input can change the query's MEANING");
        note("lookup(\"alice\")        -> " + findUserVulnerable(legit));
        note("lookup(\"x' OR '1'='1\") -> " + findUserVulnerable(attack));
        note("  ^ the injection returned EVERY user — data became code.");

        section("SAFE: parameterized query — input is only ever a bound VALUE");
        note("lookup(\"alice\")        -> " + findUserSafe(legit));
        note("lookup(\"x' OR '1'='1\") -> " + findUserSafe(attack) + "   (no match: searched literally)");

        section("The real Java API (what the safe path models)");
        note("String sql = \"SELECT * FROM users WHERE name = ?\";");
        note("try (PreparedStatement ps = conn.prepareStatement(sql)) {");
        note("    ps.setString(1, nameInput);   // BOUND as a value, never parsed as SQL");
        note("    ResultSet rs = ps.executeQuery();");
        note("}");
        note("");
        note("Never build SQL by concatenation. Same lesson for LDAP, XPath, OS commands:");
        note("use an API that separates the instruction from its parameters (Examples 03, 04).");
    }

    static String quote(String s) { return "\"" + s + "\""; }

    // ---- tiny console helpers ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
