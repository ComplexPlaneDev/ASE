import java.util.concurrent.CountDownLatch;

/**
 * Simple demonstration of CountDownLatch synchronization.
 * A latch allows one or more threads to wait until a set of operations completes.
 */
public class CountDownLatchDemo {
    
    private static final int NUM_WORKERS = 5;
    
    public static void main(String[] args) {
        // Latch to wait for all workers to be ready
        CountDownLatch startSignal = new CountDownLatch(1);
        
        // Latch to wait for all workers to complete
        CountDownLatch doneSignal = new CountDownLatch(NUM_WORKERS);
        
        System.out.println("Starting " + NUM_WORKERS + " workers...\n");
        
        for (int i = 1; i <= NUM_WORKERS; i++) {
            Thread worker = new Thread(new Worker(i, startSignal, doneSignal));
            worker.start();
        }
        
        try {
            // Simulate preparation time
            System.out.println("Main thread preparing...");
            Thread.sleep(2000);
            
            // Release all workers at once
            System.out.println("Main thread: GO! Starting all workers...\n");
            startSignal.countDown();
            
            // Wait for all workers to complete
            System.out.println("Main thread waiting for all workers to complete...\n");
            doneSignal.await();
            
            System.out.println("\n*** All workers completed! Main thread continuing ***");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread was interrupted.");
        }
    }
    
    static class Worker implements Runnable {
        private final int workerId;
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;
        
        public Worker(int workerId, CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.workerId = workerId;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }
        
        @Override
        public void run() {
            try {
                // Wait for start signal
                System.out.println("Worker " + workerId + " is ready and waiting...");
                startSignal.await();
                
                // Do work
                System.out.println("Worker " + workerId + " started working!");
                long workTime = 1000 + (workerId * 500);
                Thread.sleep(workTime);
                
                System.out.println("Worker " + workerId + " finished!");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Worker " + workerId + " was interrupted.");
            } finally {
                // Signal completion
                doneSignal.countDown();
                System.out.println("Worker " + workerId + " counted down (remaining: " + 
                                   doneSignal.getCount() + ")");
            }
        }
    }
}
