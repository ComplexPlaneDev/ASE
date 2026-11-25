import java.util.*;
import java.util.regex.Pattern;

/**
 * Running an OS command via a SHELL ("sh -c \"ping " + host + "\"") lets shell metacharacters
 * (; | && $() `) turn one command into several. Two defenses, used together:
 *   1. Pass arguments as an ARRAY to ProcessBuilder — no shell is involved, so metacharacters
 *      are literal argument text, not syntax.
 *   2. ALLOW-LIST the argument's shape anyway (defense in depth).
 *
 * This demo does NOT execute anything. It shows how the final command line is CONSTRUCTED in
 * each approach, so you can see where the injection enters and why the array form neutralizes
 * it. (Not running a process keeps the example safe, deterministic, and OS-independent.)
 */
public class Main {

    // VULNERABLE: build one string and let a shell parse it.
    static String buildShellCommand(String host) {
        return "sh -c \"ping -c 1 " + host + "\"";
    }

    // SAFE (defense in depth): allow-list the host, then pass args as a list (no shell).
    private static final Pattern HOSTNAME =
            Pattern.compile("^[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\\.[A-Za-z0-9-]+)*$");

    static List<String> buildArgvSafe(String host) {
        if (!HOSTNAME.matcher(host).matches())
            throw new IllegalArgumentException("invalid hostname (allow-list rejected it)");
        // ProcessBuilder with a LIST does NOT invoke a shell; each element is one literal arg.
        return List.of("ping", "-c", "1", host);
    }

    public static void main(String[] args) {
        String legit = "example.com";
        String attack = "example.com; rm -rf /";   // shell metacharacter ';' chains a 2nd command

        section("VULNERABLE: input flows into a SHELL command line");
        note("host = " + quote(legit));
        note("  -> " + buildShellCommand(legit));
        note("host = " + quote(attack));
        note("  -> " + buildShellCommand(attack));
        note("  ^ the shell would run ping AND THEN 'rm -rf /' — injection via ';'.");

        section("SAFE: allow-list + argument ARRAY (ProcessBuilder, no shell)");
        note("host = " + quote(legit) + "  -> argv = " + buildArgvSafe(legit));
        try {
            buildArgvSafe(attack);
        } catch (IllegalArgumentException e) {
            note("host = " + quote(attack) + "  -> REJECTED (" + e.getMessage() + ")");
        }
        note("Even without the allow-list, passing a LIST means ';' etc. are literal argument");
        note("characters, never command separators — there is no shell to interpret them.");

        section("The real Java API (what the safe path models)");
        note("ProcessBuilder pb = new ProcessBuilder(\"ping\", \"-c\", \"1\", host);  // LIST = no shell");
        note("pb.redirectErrorStream(true);");
        note("Process p = pb.start();                 // runs 'ping' directly, host is one arg");
        note("");
        note("Avoid Runtime.exec(String) and 'sh -c'. If you truly need a shell feature, build");
        note("the argument list yourself and still ALLOW-LIST every value.");
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
