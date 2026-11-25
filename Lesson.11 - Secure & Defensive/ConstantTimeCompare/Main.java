import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * The obvious way to compare two secrets — String.equals / Arrays.equals — returns as soon
 * as it finds the first differing byte. That makes its running time depend on HOW MANY
 * leading bytes matched. An attacker who can measure timing can recover a secret (token, MAC,
 * password hash) one byte at a time by trying values and keeping whichever is slightly slower.
 *
 * The fix: compare in CONSTANT TIME — always examine every byte, accumulating differences,
 * so the duration does not reveal the match length. Java provides MessageDigest.isEqual,
 * which is implemented to be constant-time for this purpose.
 */
public class Main {

    // VULNERABLE pattern: short-circuits at the first mismatch → time leaks match length.
    static boolean naiveEquals(byte[] a, byte[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;     // EARLY EXIT: timing depends on i
        }
        return true;
    }

    // SAFE pattern: examine every byte; OR the differences; no early exit.
    static boolean constantTimeEquals(byte[] a, byte[] b) {
        // Use the JDK's vetted implementation (length-safe, constant-time).
        return MessageDigest.isEqual(a, b);
    }

    // Shown for teaching: the manual constant-time idea MessageDigest.isEqual embodies.
    static boolean constantTimeEqualsManual(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;                 // folds length difference in too
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];                         // accumulate, never branch-out
        }
        return diff == 0;
    }

    public static void main(String[] args) {
        byte[] secret = "s3cr3t-api-token-abcdef".getBytes(StandardCharsets.UTF_8);
        byte[] correct = secret.clone();
        byte[] wrongLate = "s3cr3t-api-token-ABCDEF".getBytes(StandardCharsets.UTF_8); // differs late
        byte[] wrongEarly = "X3cr3t-api-token-abcdef".getBytes(StandardCharsets.UTF_8);// differs at byte 0

        section("Correctness: all three agree on the RESULT");
        note("naive(correct)          = " + naiveEquals(secret, correct));
        note("constantTime(correct)   = " + constantTimeEquals(secret, correct));
        note("constantTime(wrongLate) = " + constantTimeEquals(secret, wrongLate));
        note("manual(wrongEarly)      = " + constantTimeEqualsManual(secret, wrongEarly));

        section("The difference is TIMING, not result");
        note("naiveEquals returns at the FIRST differing byte:");
        note("  - wrongEarly (differs at byte 0)  -> returns almost immediately");
        note("  - wrongLate  (differs near the end)-> runs longer");
        note("That time difference leaks how many leading bytes were correct — a side channel.");
        note("constantTimeEquals examines every byte regardless, so timing reveals nothing.");

        section("Guidance");
        note("- Compare secrets (tokens, MACs, password hashes, signatures) with");
        note("  MessageDigest.isEqual(...) — NOT String.equals / Arrays.equals.");
        note("- The principle generalizes: security checks on secret data should be branch-free");
        note("  in the secret, so neither timing nor control flow leaks the secret's contents.");
        note("- Pair with rate limiting: side channels are far easier to exploit without it.");
    }

    // ---- tiny console helpers ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
