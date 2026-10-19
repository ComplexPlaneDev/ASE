/** Demo driver for the Strategy pattern */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println("=== Example 2: Sorting strategies ===");
        int[] data = {42, 7, 19, 3, 88, 25, 61, 14};
        Sorter sorter = new Sorter(new BubbleSort());
        sorter.sort(data);
        sorter.setStrategy(new QuickSort()); // runtime switching
        sorter.sort(data);
    }
}
