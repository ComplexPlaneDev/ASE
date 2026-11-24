import java.util.concurrent.Semaphore;

/**
 * Simple demonstration of Java Semaphore synchronization.
 * A Semaphore controls access to a shared resource through permits.
 */
public class SemaphoreDemo {
    
    private static final Semaphore semaphore = new Semaphore(3);
    
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new Worker(i));
            thread.start();
        }
    }
    
    static class Worker implements Runnable {
        private final int workerId;
        
        public Worker(int workerId) {
            this.workerId = workerId;
        }
        
        @Override
        public void run() {
            try {
                System.out.println("Worker " + workerId + " is waiting for a permit...");
                
                // Acquire a permit (blocks if none available)
                semaphore.acquire();
                
                System.out.println("Worker " + workerId + " acquired a permit! Available permits: " + semaphore.availablePermits());
                
                // Simulate work being done
                Thread.sleep(2000);
                
                System.out.println("Worker " + workerId + " is releasing the permit.");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Worker " + workerId + " was interrupted.");
            } finally {
                // Release the permit back to the semaphore
                semaphore.release();
                System.out.println("Worker " + workerId + " released the permit. Available permits: " + semaphore.availablePermits());
            }
        }
    }
}
