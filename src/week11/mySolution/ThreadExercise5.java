package week11.mySolution;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadExercise5 {
    
    /*
    Apply the technique for fixing Listing 4.14 to Listing 4.15 in the book, but to the following:
    - Create a thread-safe Counter class that stores an int and supports increment and decrement.
    - Create a new thread-safe class Point, which stores two Counter objects.
    - The two counter objects should be public.
    - Implement the method boolean areEqual() in Point, which returns true if the two counters store the same value.
    
    Question: Is the code you obtained robust with respect to client-side locking (see book)?
    
              - It is not client-side locked, since we only synchronize on the point
              therefore if we check if they are equal and someone is using the fact that 
              the counters are public in order to increment them we might not get unexpected
              results. If lets say c1 is = 0 and c2 = 0. If we then call on areEqual
              it will need to get the value of c1 which returns 0. If another thread then
              increments c2 after this, since we still need to get c2 we would read that c2
              is = 1 and the method returns false. In this case we haven't locked client-side
              Since the client can get unexpected results based on this scenario. 
              We get a simular scenario if we increment c1 after we have read it, however
              it will return true even though they arent equal.
              
              Would it help if the counters were private?
              - Yes this way the client would not be able to increment the counter while we 
              are trying to check if they are equal. 
    */
    public static void main() {
        // a small example where two threads are started
        // They each increment their respective counter in point
        // After they have incremented they will check if the counters are equal
        // This is not a perfect example to show case this, however it does some what
        // show the effects of not client-side locking.
        final int iterations = 1_000_000;
        AtomicInteger globalCounter = new AtomicInteger();
        Point p = new Point();
        Thread t1 = new Thread(() -> {
            int counter = 0;
            for(int i = 0; i < iterations; i++) {
                p.c1.increment();
                if(p.areEqual())
                    counter++;
            }
            globalCounter.addAndGet(counter);
        });
        Thread t2 = new Thread(() -> {
            int counter = 0;
            for(int i = 0; i < iterations; i++) {
                p.c2.increment();
                if(p.areEqual())
                    counter++;
            }
            globalCounter.addAndGet(counter);
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(globalCounter);

    }
    
    /*
    * Implementation of a Point which stores two public variables c1 and c2.
    */
    
    private static class Point {
        public final Counter c1, c2;
        
        public Point() {
            c1 = new Counter();
            c2 = new Counter();
        }
        
        public synchronized boolean areEqual() {
            return c1.get() == c2.get();
        }
        
    }
    
    /*
    * Simple implementation of a thread-safe counter, as we have seen before.
    */
    
    private static class Counter {
        private int i;
        
        public Counter() {
            i = 0;
        }
        
        public synchronized void increment() {
            i++;
        }
        
        public synchronized void decrement() {
            i++;
        }
        
        public synchronized int get() {
            return i;
        }
    }
}
