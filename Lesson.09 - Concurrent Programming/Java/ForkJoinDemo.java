import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

/**
 * Demonstration of ForkJoinPool framework.
 * Uses divide-and-conquer to compute sum of an array in parallel.
 */
public class ForkJoinDemo {
    
    // Threshold: if array size is below this, compute directly without forking
    private static final int THRESHOLD = 10;
    
    public static void main(String[] args) {
        // Create an array of numbers
        int[] numbers = new int[10000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1; // Fill with 1, 2, 3, ..., 100
        }
        
        System.out.println("Computing sum of array (size: " + numbers.length + ")");
        System.out.println("Using ForkJoinPool with divide-and-conquer approach\n");
        
        // Create ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();
        
        // Create the task
        SumTask task = new SumTask(numbers, 0, numbers.length);
        
        // Execute and get result
        long startTime = System.currentTimeMillis();
        long result = pool.invoke(task);
        long endTime = System.currentTimeMillis();
        
        System.out.println("\n*** Result: " + result + " ***");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        System.out.println("Pool parallelism: " + pool.getParallelism());
        
        // Verify with sequential computation
        long expectedSum = 0;
        for (int num : numbers) {
            expectedSum += num;
        }
        System.out.println("Expected sum: " + expectedSum);
        System.out.println("Correct: " + (result == expectedSum));
        
        // Shutdown pool
        pool.shutdown();
    }
    
    /**
     * RecursiveTask that computes sum of array segment using divide-and-conquer.
     */
    static class SumTask extends RecursiveTask<Long> {
        private final int[] array;
        private final int start;
        private final int end;
        
        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected Long compute() {
            int length = end - start;
            
            // Base case: compute directly if small enough
            if (length <= THRESHOLD) {
                return computeDirectly();
            }
            
            // Recursive case: split into two subtasks
            int mid = start + length / 2;
            
            System.out.println(Thread.currentThread().getName() + 
                             " splitting range [" + start + ", " + end + ") into " +
                             "[" + start + ", " + mid + ") and [" + mid + ", " + end + ")");
            
            // Create left and right subtasks
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);
            
            // Fork left task to run asynchronously
            leftTask.fork();
            
            // Compute right task in current thread
            long rightResult = rightTask.compute();
            
            // Wait for left task result
            long leftResult = leftTask.join();
            
            // Combine results
            return leftResult + rightResult;
        }
        
        /**
         * Compute sum directly without further splitting.
         */
        private long computeDirectly() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            System.out.println(Thread.currentThread().getName() + 
                             " computed range [" + start + ", " + end + ") = " + sum);
            return sum;
        }
    }
}
