import java.util.regex.Pattern;

/**
 * The #1 rule of secure programming: TREAT ALL EXTERNAL INPUT AS HOSTILE and validate it
 * as it crosses into your trusted core. We show:
 *   - DENY-LIST (block known-bad) — fragile: you can't enumerate every attack, and
 *     canonicalization tricks slip past it.
 *   - ALLOW-LIST (accept only known-good shape) — robust: anything not explicitly
 *     permitted is rejected (fail closed).
 *   - Canonicalize BEFORE validating, or the check is bypassed.
 */
public class Main {

    // DENY-LIST: try to block "bad" substrings. Fragile and incomplete by nature.
    static boolean looksSafeDenyList(String username) {
        String lower = username.toLowerCase();
        // We "block" a few things we can think of...
        return !(lower.contains("drop") || lower.contains("--") || lower.contains(";")
                 || lower.contains("<script"));
        // ...but an attacker just uses something we DIDN'T think of. Hopeless.
    }

    // ALLOW-LIST: define exactly what a valid username IS, reject everything else.
    // 3-16 chars, ASCII letters/digits/underscore only. Nothing else can get through.
    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_]{3,16}$");

    static String validateUsername(String raw) {
        if (raw == null) throw new IllegalArgumentException("username required");
        // Canonicalize FIRST (normalize unicode, trim) THEN validate the canonical form.
        String canonical = java.text.Normalizer.normalize(raw, java.text.Normalizer.Form.NFC).trim();
        if (!USERNAME.matcher(canonical).matches())
            throw new IllegalArgumentException("invalid username (allow-list rejected it)");
        return canonical;   // return a validated, normalized value to the trusted core
    }

    public static void main(String[] args) {
        String[] inputs = {
                "alice",                 // legitimate
                "x' OR '1'='1",          // SQL-injection shaped
                "../../etc/passwd",      // path-traversal shaped
                "<script>alert(1)",      // XSS shaped
                "robert'); DROP TABLE--",// classic
                "a",                     // too short
                "名前ユーザ"               // non-ASCII (rejected by our allow-list policy)
        };

        section("DENY-LIST (block known-bad): leaky — some attacks slip through");
        for (String in : inputs) {
            boolean passed = looksSafeDenyList(in);
            note(String.format("%-26s -> deny-list says %s%s", quote(in),
                    passed ? "OK" : "blocked",
                    passed && !in.equals("alice") ? "   <-- LEAKED a bad input!" : ""));
        }

        section("ALLOW-LIST (accept only known-good): robust — rejects anything unexpected");
        for (String in : inputs) {
            try {
                String ok = validateUsername(in);
                note(String.format("%-26s -> accepted as \"%s\"", quote(in), ok));
            } catch (IllegalArgumentException e) {
                note(String.format("%-26s -> REJECTED (%s)", quote(in), e.getMessage()));
            }
        }

        section("Why canonicalize first");
        note("'../%2e%2e/' or mixed-case/unicode variants can bypass a naive deny-list check.");
        note("Normalizing to a canonical form BEFORE the check removes those evasions.");
        note("");
        note("Rule: validate on the way IN with an ALLOW-LIST; reject on any doubt (fail closed).");
        note("Validation is NOT output encoding — you still encode/escape at each sink (Example 02+).");
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
