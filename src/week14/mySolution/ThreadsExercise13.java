package week14.mySolution;

import Week12.mySolution.Words;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */
public class ThreadsExercise13 {

    /*
    Modify Threads/cp/WalkExecutor such that all threads stop processing as soon as the word "nulla" is found.
    Hint: Use an atomic boolean shared by all threads. Try not to make it a static field, but rather a local variable within the main method.
     */
    public static void main() {
        // word -> number of times it appears over all files
        Map< String, Integer> occurrences = new ConcurrentHashMap<>();
//		ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        // We create an AtomicBoolean that all threads can see and use.
        AtomicBoolean foundNulla = new AtomicBoolean(); // initial value = false.

        try {
            Files.walk(Paths.get("data"))
                    .filter(Files::isRegularFile)
                    .forEach(filepath -> {
                        executor.submit(() -> {
                            computeOccurrences(filepath, occurrences, foundNulla);
                            // If we found nulla in this call to compute occurences we 
                            // shut the executor down immediatly. 
                            if(foundNulla.get()) {
                                executor.shutdownNow();
                            }
                        });
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//		occurrences.forEach( (word, n) -> System.out.println( word + ": " + n ) );
    }

    private static void computeOccurrences(Path textFile, Map< String, Integer> occurrences, AtomicBoolean foundNulla) {
        try {
            Files.lines(textFile)
                    .flatMap(Words::extractWords)
                    .map(String::toLowerCase)
                    // We use a take while here that will make sure that the foreach only gets elements 
                    // while we haven't found nulla.
                    .takeWhile(s -> {
                        boolean isNulla = s.equals("nulla");
                        return foundNulla.compareAndSet(false, isNulla) && !isNulla;
                    })
                    .forEach(s -> {
                        occurrences.merge(s, 1, Integer::sum);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
