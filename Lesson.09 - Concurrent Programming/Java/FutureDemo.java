import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of Future with ExecutorService.
 * Futures represent the result of an asynchronous computation.
 */
public class FutureDemo {
    
    private static final int POOL_SIZE = 3;
    
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        
        System.out.println("Submitting tasks that return results...\n");
        
        // Submit tasks that return Future objects
        List<Future<Integer>> futures = new ArrayList<>();
        
        for (int i = 1; i <= 5; i++) {
            Future<Integer> future = executor.submit(new CalculationTask(i));
            futures.add(future);
        }
        
        System.out.println("All tasks submitted. Processing results...\n");
        
        // Retrieve results from futures
        int sum = 0;
        for (int i = 0; i < futures.size(); i++) {
            try {
                // Get result (blocks until task completes)
                Integer result = futures.get(i).get();
                System.out.println("Task " + (i + 1) + " result: " + result);
                sum += result;
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupted while waiting for result");
            } catch (ExecutionException e) {
                System.err.println("Task threw exception: " + e.getCause());
            }
        }
        
        System.out.println("\nTotal sum of all results: " + sum);
        
        // Demonstrate Future with timeout
        demonstrateTimeout(executor);
        
        // Shutdown executor
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println("\n*** All tasks completed! ***");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Demonstrates using Future.get() with timeout.
     */
    private static void demonstrateTimeout(ExecutorService executor) {
        System.out.println("\n--- Demonstrating Future with timeout ---");
        
        Future<String> future = executor.submit(new SlowTask());
        
        try {
            // Try to get result with 2 second timeout
            String result = future.get(2, TimeUnit.SECONDS);
            System.out.println("Result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Task timed out! Cancelling...");
            future.cancel(true); // Interrupt the task
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("Future cancelled: " + future.isCancelled());
        System.out.println("Future done: " + future.isDone());
    }
    
    /**
     * A task that performs a calculation and returns a result.
     */
    static class CalculationTask implements Callable<Integer> {
        private final int taskId;
        
        public CalculationTask(int taskId) {
            this.taskId = taskId;
        }
        
        @Override
        public Integer call() throws Exception {
            String threadName = Thread.currentThread().getName();
            System.out.println("Task " + taskId + " calculating on " + threadName + "...");
            
            // Simulate computation
            Thread.sleep(1000 + (taskId % 3) * 500);
            
            int result = taskId * taskId; // Simple calculation: square of taskId
            System.out.println("Task " + taskId + " computed: " + result);
            
            return result;
        }
    }
    
    /**
     * A slow task used to demonstrate timeout.
     */
    static class SlowTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("Slow task started (will take 5 seconds)...");
            Thread.sleep(5000);
            return "Completed!";
        }
    }
}
