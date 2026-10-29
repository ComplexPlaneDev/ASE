// A tour of EVERY cast/conversion kind and its failure mode, then a refactor
// showing how good design DELETES casts (instanceof-ladder -> polymorphism).
//
// Everything here compiles; the risky casts fail at RUNTIME and are caught so
// the program runs to completion. Casts that cannot even compile are shown as
// comments (and collected in NonCompiling_Examples.java.txt).
//

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== 1. Widening primitive (implicit, safe) ===");
        int i = 1_000;
        long l = i;            // int -> long
        double d = l;          // long -> double
        System.out.println("  int " + i + " -> long " + l + " -> double " + d + "  (no loss)");

        System.out.println("\n=== 2. Narrowing primitive (explicit, SILENTLY lossy) ===");
        double pi = 3.9;
        int truncated = (int) pi;          // -> 3 (truncates, no rounding, no error)
        int overflow = (int) 3_000_000_000L; // wraps around silently
        System.out.println("  (int) 3.9           = " + truncated + "   <- data lost, no warning");
        System.out.println("  (int) 3_000_000_000 = " + overflow + "   <- overflow, no warning");
        // Safer: throws on overflow instead of wrapping:
        try {
            Math.toIntExact(3_000_000_000L);
        } catch (ArithmeticException e) {
            System.out.println("  Math.toIntExact(...) -> ArithmeticException (the safe choice)");
        }

        System.out.println("\n=== 3. Boxing / unboxing (implicit; null unboxes to NPE) ===");
        Integer boxed = 42;    // autobox
        int unboxed = boxed;   // auto-unbox
        System.out.println("  Integer 42 <-> int " + unboxed);
        try {
            Integer nothing = null;
            int boom = nothing;            // unboxing null
            System.out.println(boom);
        } catch (NullPointerException e) {
            System.out.println("  unboxing null -> NullPointerException");
        }

        System.out.println("\n=== 4. Upcast (reference widening, implicit, always safe) ===");
        Dog dog = new Dog();
        Animal asAnimal = dog;             // Dog -> Animal, no cast needed
        System.out.println("  Dog upcast to Animal: " + asAnimal.getClass().getSimpleName());

        System.out.println("\n=== 5. Downcast (reference narrowing, runtime-CHECKED) ===");
        Animal a1 = new Dog();
        Dog backToDog = (Dog) a1;          // OK: a1 really is a Dog
        System.out.println("  (Dog) a1 succeeded: " + backToDog.getClass().getSimpleName());
        Animal a2 = new Cat();
        try {
            Dog wrong = (Dog) a2;          // compiles; fails at runtime
            System.out.println(wrong);
        } catch (ClassCastException e) {
            System.out.println("  (Dog) a_cat -> ClassCastException (the runtime check)");
        }

        System.out.println("\n=== 6. The modern replacement: instanceof pattern (no cast) ===");
        if (a1 instanceof Dog dd) {        // test + bind in one, no (Dog) cast
            System.out.println("  instanceof Dog d -> bound d directly, d.fetch() = " + dd.fetch());
        }

        System.out.println("\n=== 7. Unchecked / generic cast (erasure hole: NOT checked) ===");
        List<String> strings = new ArrayList<>();
        strings.add("ok");
        @SuppressWarnings("unchecked")     // acceptable ONLY because we isolate + comment it
        List<Object> asObjects = (List<Object>) (List<?>) strings;  // heap pollution risk
        asObjects.add(123);                // compiles! corrupts the List<String>
        try {
            String s = strings.get(1);     // CCE surfaces far from the real mistake
            System.out.println(s);
        } catch (ClassCastException e) {
            System.out.println("  unchecked cast let an Integer in; reading as String -> CCE");
            System.out.println("  (this is why unchecked casts are a design smell)");
        }

        System.out.println("\n=== 8. Reflective CHECKED cast (type-token safe) ===");
        Object obj = "hello";
        String viaCast = String.class.cast(obj);   // checked; CCE if wrong, but type-safe API
        System.out.println("  String.class.cast(obj) = " + viaCast);

        System.out.println("\n=== 9. BAD vs GOOD design: instanceof-ladder -> polymorphism ===");
        List<Animal> zoo = List.of(new Dog(), new Cat(), new Dog());
        System.out.println("  BAD  (ladder of downcasts): " + speakBad(zoo));
        System.out.println("  GOOD (polymorphism):        " + speakGood(zoo));
        System.out.println("""
                    The GOOD version has ZERO casts: each Animal knows its own sound.
                    Adding a new Animal requires no edits to the caller — the ladder
                    version would need another instanceof branch (and could forget one).""");
    }

    // ---- types for the demo ----
    static class Animal {
        String sound() { return "..."; }   // polymorphic hook
    }
    static class Dog extends Animal {
        @Override String sound() { return "woof"; }
        String fetch() { return "fetched the ball"; }
    }
    static class Cat extends Animal {
        @Override String sound() { return "meow"; }
    }

    // BAD: downcast ladder — brittle, not exhaustive, repeats type knowledge.
    static String speakBad(List<Animal> animals) {
        StringBuilder sb = new StringBuilder();
        for (Animal a : animals) {
            if (a instanceof Dog) sb.append("woof ");
            else if (a instanceof Cat) sb.append("meow ");
            else sb.append("? ");
        }
        return sb.toString().trim();
    }

    // GOOD: polymorphism — no casts, open to new subtypes.
    static String speakGood(List<Animal> animals) {
        StringBuilder sb = new StringBuilder();
        for (Animal a : animals) sb.append(a.sound()).append(' ');
        return sb.toString().trim();
    }
}
