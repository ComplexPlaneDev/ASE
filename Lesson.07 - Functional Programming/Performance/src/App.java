import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MyInteger {
    private int value;

    public MyInteger() {
        this.value = 0;
    }

    public MyInteger(int v) {
        this.value = v;
    }

    public int get() {
        return this.value;
    }

    public void set(int v) {
        this.value = v;
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        long result = 0;
        final List<Integer> numList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final int TRIES = 100000000;

        long start = System.currentTimeMillis();
        for (int cnt = 0; cnt < TRIES; ++cnt) {
            for (int i = 0; i < numList.size(); i++) {
                if (numList.get(i) % 2 == 0) {
                    result += numList.get(i) * 10;
                }
            }
        }
        System.out.println(result);
        System.out.println("Plain run: " + String.valueOf(System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        result = 0;
        for (int cnt = 0; cnt < TRIES; ++cnt) {
            result += numList.stream().filter(n -> n % 2 == 0).map(n -> n * 10).reduce(0, (a, b) -> a + b);
        }
        System.out.println(result);
        System.out.println("Functional (no copies) run: " + String.valueOf(System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        result = 0;
        for (int cnt = 0; cnt < TRIES; ++cnt) {
            result += numList.stream().filter(n -> n % 2 == 0).mapToLong(n -> n * 10).sum();
        }
        System.out.println(result);
        System.out.println("Functional (optimized streams) run: " + String.valueOf(System.currentTimeMillis() - start));

        final int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        start = System.currentTimeMillis();
        result = 0;
        for (int cnt = 0; cnt < TRIES; ++cnt) {
            result += Arrays.stream(numbers).filter(n -> n % 2 == 0).map(n -> n * 10).asLongStream().sum();
        }
        System.out.println(result);
        System.out.println("Functional (primitive streams) run: " + String.valueOf(System.currentTimeMillis() - start));


        System.out.println("-----");
        List<Integer> intSequence = new ArrayList<Integer>();
        intSequence.add(1);
        intSequence.add(2);
        intSequence.add(3);
        intSequence.add(4);
        intSequence.add(5);
        intSequence.add(6);
        intSequence.add(7);
        intSequence.add(8);
        intSequence.add(9);

        printList("intSequence", intSequence, n -> String.valueOf(n));
        List<Integer> evenSequence = intSequence.stream().filter(n -> n % 2 == 0).toList();
        try {
            evenSequence.add(10);
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot add 10");
        }
        printList("evenSequence", evenSequence, n -> String.valueOf(n));

        System.out.println("-----");
        List<MyInteger> myIntegers = Stream.iterate(1, n -> n + 1).limit(10).map(MyInteger::new).toList();
        printList("myIntegers", myIntegers, n -> String.valueOf(n.get()));
        List<MyInteger> myEvenSequence = myIntegers.stream().filter(n -> n.get() % 2 == 0).toList();
        try {
            myEvenSequence.add(new MyInteger(10));
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot add MyInteger(10)");
        }
        myEvenSequence.get(0).set(-1000);
        printList("myIntegers", myIntegers, n -> String.valueOf(n.get()));
        printList("myEvenSequence", myEvenSequence, n -> String.valueOf(n.get()));


        System.out.println("-----");
        myIntegers.get(1).set(2);
        printList("myIntegers", myIntegers, n -> String.valueOf(n.get()));
        List<MyInteger> myCopiedEvenSequence = myIntegers.stream().filter(n -> n.get() % 2 == 0).map(n -> new MyInteger(n.get())).toList();
        try {
            myCopiedEvenSequence.add(new MyInteger(10));
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot add MyInteger(10)");
        }
        myCopiedEvenSequence.get(0).set(-1000);
        printList("myIntegers", myIntegers, n -> String.valueOf(n.get()));
        printList("myCopiedEvenSequence", myCopiedEvenSequence, n -> String.valueOf(n.get()));

        System.out.println("-----");
        printList("myIntegers", myIntegers, n -> String.valueOf(n.get()));
        List<MyInteger> myMutableCopiedEvenSequence = myIntegers.stream().filter(n -> n.get() % 2 == 0).map(n -> new MyInteger(n.get())).collect(Collectors.toCollection(ArrayList::new));
        try {
            myMutableCopiedEvenSequence.add(new MyInteger(10));
        } catch (UnsupportedOperationException e) {
            System.out.println("I may add a MyInteger(10)");
        }
        myMutableCopiedEvenSequence.get(0).set(-1000);
        printList("myIntegers", myIntegers, n -> String.valueOf(n.get()));
        printList("myMutableCopiedEvenSequence", myMutableCopiedEvenSequence, n -> String.valueOf(n.get()));

        final List<Integer> numList2 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final List<Integer> out = numList2.stream().filter(n -> n % 2 == 0).map(n -> n * 10).toList();
        printList("a", numList2, n -> String.valueOf(n));
        printList("b", out, n -> String.valueOf(n));
    }

    private static <T> void printList(String name, List<T> list, Function<T, String> converter) {
        System.out.println(name + ":");
        list.forEach(n -> System.out.print(converter.apply(n) + " "));
        System.out.println();
    }
}

