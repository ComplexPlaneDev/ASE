import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Simple demonstration of CyclicBarrier synchronization.
 * A barrier allows threads to wait for each other at a common point before proceeding.
 */
public class BarrierDemo {
    
    private static final int NUM_WORKERS = 4;
    
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(NUM_WORKERS, () -> {
            System.out.println("\n*** All workers reached the barrier! Proceeding to next phase ***\n");
        });
        
        System.out.println("Starting " + NUM_WORKERS + " workers...\n");
        
        for (int i = 1; i <= NUM_WORKERS; i++) {
            Thread worker = new Thread(new Worker(i, barrier));
            worker.start();
        }
    }
    
    static class Worker implements Runnable {
        private final int workerId;
        private final CyclicBarrier barrier;
        
        public Worker(int workerId, CyclicBarrier barrier) {
            this.workerId = workerId;
            this.barrier = barrier;
        }
        
        @Override
        public void run() {
            try {
                performTask("Phase 1", 1000 + (workerId * 500));
                System.out.println("Worker " + workerId + " waiting at barrier (Phase 1)...");
                barrier.await(); // Wait for all threads
                
                performTask("Phase 2", 800 + (workerId * 300));
                System.out.println("Worker " + workerId + " waiting at barrier (Phase 2)...");
                barrier.await(); // Wait for all threads again
                
                performTask("Phase 3", 500 + (workerId * 200));
                System.out.println("Worker " + workerId + " completed all phases!");
                
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
                System.err.println("Worker " + workerId + " was interrupted or barrier broken.");
            }
        }
        
        private void performTask(String phase, long duration) throws InterruptedException {
            System.out.println("Worker " + workerId + " starting " + phase + "...");
            Thread.sleep(duration);
            System.out.println("Worker " + workerId + " finished " + phase);
        }
    }
}
