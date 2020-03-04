package week10.inClass;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadExercise1 {

    /*
    - Create a Counter class storing an integer (a field called i), with an increment and decrement method.
    - Make Counter thread-safe (see Chapter 2 in the book)
    - Does it make a different to declare i private or public?
     */
    public static void main() {
        int numberOfTimes = 100_000;
        Counter c = new Counter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < numberOfTimes; i++) {
                c.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < numberOfTimes; i++) {
                c.increment();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(c.getCounter());

    }

    private static class Counter {

        private int i;

        public Counter() {
            i = 0;
        }

        public synchronized void increment() {
            i++;
        }

        public synchronized void decrement() {
            i--;
        }

        public synchronized int getCounter() {
            return i;
        }
    }
    
    private static class Counter2 {
        
        public final AtomicInteger i;
        
        public Counter2() {
            i = new AtomicInteger();
        }
        
        public void increment() {
            i.incrementAndGet();
        }
        
        public void decrement() {
            i.decrementAndGet();
        }
        
        public int getCounter() {
            return i.get();
        }
    }
}
