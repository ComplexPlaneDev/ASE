import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simple demonstration of Executors.newFixedThreadPool().
 * A fixed thread pool reuses a fixed number of threads to execute tasks.
 */
public class FixedThreadPoolDemo {
    
    private static final int POOL_SIZE = 3;
    private static final int NUM_TASKS = 10;
    
    public static void main(String[] args) {
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        
        System.out.println("Created fixed thread pool with " + POOL_SIZE + " threads");
        System.out.println("Submitting " + NUM_TASKS + " tasks...\n");
        
        // Submit 10 tasks to the pool
        for (int i = 1; i <= NUM_TASKS; i++) {
            Task task = new Task(i);
            executor.submit(task);
        }
        
        // Shutdown the executor (no new tasks accepted)
        executor.shutdown();
        System.out.println("\nExecutor shutdown initiated. Waiting for tasks to complete...\n");
        
        try {
            // Wait for all tasks to complete (with timeout)
            if (executor.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println("*** All tasks completed successfully! ***");
            } else {
                System.out.println("Timeout occurred before all tasks completed");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread was interrupted while waiting");
        }
    }
    
    /**
     * A simple task that simulates some work.
     */
    static class Task implements Runnable {
        private final int taskId;
        
        public Task(int taskId) {
            this.taskId = taskId;
        }
        
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("Task " + taskId + " started by " + threadName);
            
            try {
                // Simulate work with varying duration
                long workTime = 1000 + (taskId % 3) * 500;
                Thread.sleep(workTime);
                
                System.out.println("Task " + taskId + " completed by " + threadName + 
                                   " (took " + workTime + "ms)");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task " + taskId + " was interrupted");
            }
        }
    }
}
