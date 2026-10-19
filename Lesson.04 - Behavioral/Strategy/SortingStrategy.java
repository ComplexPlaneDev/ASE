/**
 * Strategy interface - the common contract of all sorting algorithms.
 * Each concrete strategy sorts the given array in place.
 */
public interface SortingStrategy {

    /** Sorts {@code array} in ascending order, in place. */
    void sort(int[] array);

    /** Human-readable name of the algorithm, used for logging. */
    String name();
}
