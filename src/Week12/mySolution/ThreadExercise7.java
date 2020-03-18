package Week12.mySolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */
public class ThreadExercise7 {

    /*
    - Modify Threads/cp/threads/SynchronizedMap such that:
    	* Each threads also counts the total number of times that any word
    	  starting with the letter "L" appears.
    	* Each thread should have its own total (no shared global counter).
    	* The sum of all totals is printed at the end.
     */
    public static void main() {
        // Since we cant use a global counter for all the different files
        // We use a global accumulator that takes the result from each file
        // After they are done with their respective file and save their result
        // This way they dont share a counter, but rather a place to store their counter
        // when they are done. 
        AtomicInteger accumulator = new AtomicInteger(); // Default value = 0
        
        // word -> number of times that it appears over all files
        Map< String, Integer> occurrences = new HashMap<>();

        List< String> filenames = List.of(
                "text1.txt",
                "text2.txt",
                "text3.txt",
                "text4.txt",
                "text5.txt",
                "text6.txt",
                "text7.txt",
                "text8.txt",
                "text9.txt",
                "text10.txt"
        );

        CountDownLatch latch = new CountDownLatch(filenames.size());

        filenames.stream()
                .map(filename -> new Thread(() -> {
            int count = computeOccurrences(filename, occurrences);
            accumulator.addAndGet(count);
            latch.countDown();
        }))
                .forEach(Thread::start);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(accumulator);
    }

    /*
    * We modify this method to return the number of times a word starting with L occurs. 
    * In order for this to work we change the mapping, such that it check if the word
    * starts with 'L' before changing the word to lower case.
     */
    private static int computeOccurrences(String filename, Map< String, Integer> occurrences) {
        // Since we can't change a local variable inside a lambda expression
        // we can't just do "int count = 0;". This is simply not possible inside
        // a lamda. Therefore we create a class counter like we have seen before
        // to store the count for each method call. We then return the int value
        // of this counter.
        Counter counter = new Counter();
        try {
            Files.lines(Paths.get(filename))
                    .flatMap(Words::extractWords)
                    .map(s -> {
                        if (s.startsWith("L")) {
                            counter.increment();
                        }
                        return s.toLowerCase();
                    })
                    .forEach(s -> {
                        synchronized (occurrences) {
                            occurrences.merge(s, 1, Integer::sum);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter.getValue();
    }

    private static class Counter {

        // Simple counter class. Doesn't need to be thread-safe as we use
        // a new instance of counter for each thread.
        private int i;

        public Counter() {
            i = 0;
        }

        public void increment() {
            i++;
        }

        public int getValue() {
            return i;
        }
    }
}
