import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Simple demonstration of Condition Variables (java.util.concurrent.locks.Condition).
 * Implements a bounded queue where producers and consumers coordinate using conditions.
 */
public class ConditionVariableDemo {
    
    public static void main(String[] args) {
        BoundedQueue queue = new BoundedQueue(5);
        
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new Producer(queue, i));
            producer.start();
        }
        
        for (int i = 1; i <= 2; i++) {
            Thread consumer = new Thread(new Consumer(queue, i));
            consumer.start();
        }
    }
    
    static class BoundedQueue {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;
        private final Lock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();  // Signaled when queue is not full
        private final Condition notEmpty = lock.newCondition(); // Signaled when queue is not empty
        
        public BoundedQueue(int capacity) {
            this.capacity = capacity;
        }
        
        public void put(int item) throws InterruptedException {
            lock.lock();
            try {
                // Wait while queue is full
                while (queue.size() == capacity) {
                    System.out.println("  [Queue Full] Waiting to put item " + item);
                    notFull.await();
                }
                
                queue.add(item);
                System.out.println("✓ Produced: " + item + " (Queue size: " + queue.size() + ")");
                
                // Signal that queue is not empty
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }
        
        public int take() throws InterruptedException {
            lock.lock();
            try {
                // Wait while queue is empty
                while (queue.isEmpty()) {
                    System.out.println("  [Queue Empty] Waiting to take item");
                    notEmpty.await();
                }
                
                int item = queue.remove();
                System.out.println("✓ Consumed: " + item + " (Queue size: " + queue.size() + ")");
                
                // Signal that queue is not full
                notFull.signal();
                
                return item;
            } finally {
                lock.unlock();
            }
        }
    }
    
    static class Producer implements Runnable {
        private final BoundedQueue queue;
        private final int producerId;
        
        public Producer(BoundedQueue queue, int producerId) {
            this.queue = queue;
            this.producerId = producerId;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    int item = producerId * 100 + i;
                    queue.put(item);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class Consumer implements Runnable {
        private final BoundedQueue queue;
        private final int consumerId;
        
        public Consumer(BoundedQueue queue, int consumerId) {
            this.queue = queue;
            this.consumerId = consumerId;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i < 7; i++) {
                    queue.take();
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
