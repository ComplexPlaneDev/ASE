// ITERATOR, generified. You already built Iterator with concrete types; this
// shows the before/after. Generics turn a cast-laden, Object-based iterator
// into a type-safe one, and let us write ALGORITHMS over any Iterable<T>.

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DP_Iterator {

    // ---------- BEFORE: a pre-generics style iterator (Object-based) ----------
    // Every read needs a cast, and nothing stops a String sneaking into an
    // "int" collection. This is what Iterator looked like before Java 5.
    interface RawIterator {
        boolean hasNext();
        Object next();                 // returns Object -> caller must cast
    }

    // ---------- AFTER: the generic Iterable<T> / Iterator<T> (JDK built-in) ----------
    // A fixed-capacity ring buffer that is itself Iterable<T>, so it works with
    // the for-each loop and every java.util algorithm — with zero casts.
    static final class Buffer<T> implements Iterable<T> {
        private final Object[] data;   // backing store is Object[] (erasure reality)
        private int size;

        Buffer(int capacity) { data = new Object[capacity]; }

        void add(T item) {
            if (size == data.length) throw new IllegalStateException("full");
            data[size++] = item;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {           // generic anonymous iterator
                private int cursor = 0;
                @Override public boolean hasNext() { return cursor < size; }
                @Override @SuppressWarnings("unchecked")
                public T next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    return (T) data[cursor++]; // the ONE cast lives here, proven safe by add()
                }
            };
        }
    }

    // A generic algorithm over ANY Iterable<T> — the real payoff of the pattern.
    static <T> void print(Iterable<T> src) {
        var it = src.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Buffer<String> names = new Buffer<>(4);
        names.add("Ada"); names.add("Grace"); names.add("Alan");
        print(names);

        Buffer<Integer> nums = new Buffer<>(8);
        for (int i = 1; i <= 6; i++) nums.add(i);
        print(nums);
    }
}
