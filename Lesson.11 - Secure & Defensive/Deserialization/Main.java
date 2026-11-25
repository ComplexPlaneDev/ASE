import java.io.*;
import java.util.*;

/**
 * ObjectInputStream.readObject() reconstructs arbitrary classes from bytes and runs code
 * (readObject/readResolve of library classes) DURING reconstruction — before you can
 * type-check the result. Hostile bytes can chain these "gadgets" into remote code execution.
 *
 * Defenses, in order:
 *   1. Don't use native serialization for untrusted data — use JSON/Protobuf mapped to known
 *      types.
 *   2. If you must, install an ALLOW-LIST ObjectInputFilter: permit only the classes you
 *      expect, reject everything else.
 *
 * This demo serializes a legitimate object, then deserializes (a) with no filter and
 * (b) with an allow-list filter, and shows the filter REJECTING an unexpected class. No real
 * gadget/exploit is used — we stand in a disallowed class (java.util.Random) for "unexpected".
 */
public class Main {

    // A legitimate, expected data type.
    record Account(String user, int balanceCents) implements Serializable {}

    static byte[] serialize(Object o) throws IOException {
        var baos = new ByteArrayOutputStream();
        try (var oos = new ObjectOutputStream(baos)) { oos.writeObject(o); }
        return baos.toByteArray();
    }

    // UNSAFE: deserialize with no restrictions — any class on the classpath can be rebuilt.
    static Object deserializeUnfiltered(byte[] bytes) throws IOException, ClassNotFoundException {
        try (var ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return ois.readObject();
        }
    }

    // SAFE: install an allow-list filter. Only the classes we name may be reconstructed;
    // anything else is rejected before it can run gadget code. Also bound depth/size.
    static Object deserializeFiltered(byte[] bytes, String allowedClassPattern)
            throws IOException, ClassNotFoundException {
        try (var ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(
                    allowedClassPattern + ";java.base/*;!*");  // allow listed + core; reject the rest
            ois.setObjectInputFilter(filter);
            return ois.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] good = serialize(new Account("alice", 10_00));
        byte[] unexpected = serialize(new Random(42));   // stands in for an "unexpected" class

        section("UNFILTERED deserialization accepts ANY class (the danger)");
        note("Account bytes   -> " + deserializeUnfiltered(good));
        note("Random  bytes   -> " + deserializeUnfiltered(unexpected)
                + "   <-- an unexpected class was rebuilt without question");
        note("With a real gadget library on the classpath, that step can mean code execution.");

        section("ALLOW-LIST filter: only expected classes are reconstructed");
        String allow = Account.class.getName();   // the ONLY app class we permit
        note("Account bytes   -> " + deserializeFiltered(good, allow) + "   (allowed)");
        try {
            deserializeFiltered(unexpected, allow);
        } catch (Exception e) {
            note("Random  bytes   -> REJECTED by filter ("
                    + e.getClass().getSimpleName() + ") — gadget never got to run");
        }

        section("Preferred approach: avoid native serialization for untrusted data");
        note("- Exchange data as JSON/Protobuf with an explicit schema, bound to known types.");
        note("- A global filter can be set with -Djdk.serialFilter=... or ObjectInputFilter.Config.");
        note("- Always bound maxdepth / maxrefs / maxarray to stop deserialization 'bombs'.");
        note("");
        note("Key insight: deserialization RUNS CODE before you can validate — so control what");
        note("is ALLOWED to be built, or don't use the mechanism across a trust boundary at all.");
    }

    // ---- tiny console helpers ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
