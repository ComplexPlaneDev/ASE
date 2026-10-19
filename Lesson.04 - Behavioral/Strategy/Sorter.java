import java.util.Arrays;

/**
 * Context - delegates the actual sorting work to the configured strategy.
 * The algorithm can be fixed at construction time or swapped at runtime.
 */
public class Sorter {

    private SortingStrategy strategy;

    public Sorter(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    /** Runtime strategy switching, an optional part of the pattern. */
    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    /** Returns a sorted copy of {@code source} and logs which algorithm ran. */
    public int[] sort(int[] source) {
        int[] work = source.clone();
        long startNanos = System.nanoTime();
        strategy.sort(work);
        long elapsedNanos = System.nanoTime() - startNanos;
        System.out.printf("  %-12s -> %s, took %d ns%n",
                strategy.name(), Arrays.toString(work), elapsedNanos);
        return work;
    }
}
