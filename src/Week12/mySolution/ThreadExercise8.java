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
public class ThreadExercise8 {

    /*
    - As ThreadExercise7, but now use a global counter among all threads instead.
    - Reason about the pros and cons of the two concurrency strategies
      (write them down).
    - So in these examples there will only be an upside to not share the same global counter.
      The reason for this is that when the threads find a word that starts with "L" they all
      share the same counter and there is a high chance of multiple threads finding a word
      at the same time and therefore they will each have to wait for another thread to increment 
      the counter before they are allowed to. This can be mostly removed in the ThreadExercise7
      since the only time that this occurs is when the threads are done with the
      entire file and can just add the number once. This way we reduce the amount of times that a 
      thread potentially have to wait for another. 
      In short we lower the amount of thread contention in ThreadExercise7.
      Thread contention is a big part of concurrency. Make sure you understand what this means.
     */
    public static void main() {
        // Since here we use one single counter, we change the computeOccurences method
        // To take the counter as argument such that each thread can use it.
        AtomicInteger counter = new AtomicInteger(); // Default value = 0

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
            computeOccurrences(filename, occurrences, counter);
            latch.countDown();
        }))
                .forEach(Thread::start);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
    }
    
    /*
    * Here we do something simular to ThreadExercise7, however we use the counter that is given in the argument
    * to increment when we find a word starting with "L" instead. 
    */

    private static void computeOccurrences(String filename, Map< String, Integer> occurrences, AtomicInteger counter) {
        try {
            Files.lines(Paths.get(filename))
                    .flatMap(Words::extractWords)
                    .map(s -> {
                        if (s.startsWith("L")) {
                            counter.incrementAndGet();
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
    }
}
