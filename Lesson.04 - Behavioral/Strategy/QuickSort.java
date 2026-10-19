/** ConcreteStrategy - quicksort with Lomuto partition, average O(n log n). */
public class QuickSort implements SortingStrategy {

    @Override
    public void sort(int[] a) {
        if (a.length > 1) {
            quicksort(a, 0, a.length - 1);
        }
    }

    private void quicksort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int p = partition(a, lo, hi);
        quicksort(a, lo, p - 1);
        quicksort(a, p + 1, hi);
    }

    private int partition(int[] a, int lo, int hi) {
        int pivot = a[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (a[j] <= pivot) {
                i++;
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
        }
        int tmp = a[i + 1];
        a[i + 1] = a[hi];
        a[hi] = tmp;
        return i + 1;
    }

    @Override
    public String name() {
        return "QuickSort";
    }
}
