package week10.mySolution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        Thread t;
        int counts = 100_000;
        CounterNOTTHREADSAFE c = new CounterNOTTHREADSAFE();
        Counter1 c1 = new Counter1();
        Counter2 c2 = new Counter2();
        List<Thread> threads = new ArrayList();
        // The following creates two threads for each counter
        // All threads have 1 job and that is to increment the counter
        // counts times.
        for (int i = 0; i < 2; i++) {
            t = new Thread(() -> {
                for (int j = 0; j < counts; j++) {
                    c1.increment();
                }
            });
            threads.add(t);
            t = new Thread(() -> {
                for(int j = 0; j < counts; j++) {
                    c2.increment();
                }
            });
            threads.add(t);
            t = new Thread(() -> {
                for(int j = 0; j < counts; j++) {
                    c.increment();
                }
            });
            threads.add(t);
        }
        
        // Time to run the threads
        threads.forEach((thread) -> thread.start());
        // Wait for them to finish
        threads.forEach((thread) -> {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        
        // Lets print all counters after they are done.
        // We would expect each counter to be at counts * 2
        // However you should see that CounterNOTTHREADSAFE is not.
        // If this is not the case, try increase counts (your pc might just be too fast).
        System.out.println("Counter1: " + c1.getCounter());
        System.out.println("Counter2: " + c2.getCounter());
        System.out.println("CounterNOTTHREADSAFE: " + c.getCounter());
    }
    
    private static class Counter1 {
        /*
        * This is one example of how the counter could be done threadsafe.
        * Here its simply done by making all methods synchronized, such that
        * only one thread is allowed to use the method at a time.
        * In this example the counter can't be public, because then the threads
        * could just access the counter directly and it would defeat the purpose
        * of making the counter threadsafe through the methods.
        */
        private int i;
        
        public Counter1() {
            i = 0;
        }
        
        public synchronized void increment() {
            i++;
        }
        
        public synchronized void decrement() {
            i--;
        }
        
        // This method needs to be synchronized too in order to get a correct read
        // (stale data)
        public synchronized int getCounter() {
            return i;
        }
    }
    
    private static class Counter2 {
        
        /*
        * This is another way of making the counter threadsafe.
        * Here we make use of the wrapper class from java called
        * Atomic Integer. This class is already threadsafe, so if 
        * we only use methods offered by that class we extend the 
        * thread safety to our class. 
        * Here the variable can actually be public, but only if its also final.
        * Since AtomicInteger is a class the "i" is actually a pointer and therefore
        * if its not final a thread could make the pointer point to a new variable
        * or null. This could leave the class in a state where methods would fail
        * (if the variable is null we can't call on things such as incrementAndGet)
        */
        
        public final AtomicInteger i;
        
        public Counter2() {
            // if not specified the initial value is 0.
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
    
    private static class CounterNOTTHREADSAFE {
        
        /*
        * Not thread-safe version, used to show the difference in results.
        */
        
        private int i;
        
        public CounterNOTTHREADSAFE() {
            i = 0;
        }
        
        public void increment() {
            i++;
        }
        
        public void decrement() {
            i--;
        }
        
        public int getCounter() {
            return i;
        }
    }
}
