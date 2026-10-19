/** ConcreteStrategy - bubble sort, quadratic time in the worst case. */
public class BubbleSort implements SortingStrategy {

    @Override
    public void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break; // already sorted
            }
        }
    }

    @Override
    public String name() {
        return "BubbleSort";
    }
}
