package week14.mySolution;

import Week12.mySolution.Words;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */
public class ThreadsExercise11 {

    /*
    Modify ThreadsExercise9 to use executors.
    Try different kinds of executor (cached thread pool or fixed thread pool) and different fixed pool sizes.
    Which executor runs faster? <- see below main
    Can you explain why? <- see below main
     */
    public static void main() {
        Map<Character, Set<String>> occurrences = new ConcurrentHashMap<>();

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

        // In order to do this we first remove the countdownLatch since the executor will keep track of that itself.
        //CountDownLatch latch = new CountDownLatch(filenames.size());
        
        // We then create the executor service of our choosing and submit all tasks to it.
        // We can use different executors for different problems. Try out both on your PC to see which runs faster on your PC!
        
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //ExecutorService executor = Executors.newCachedThreadPool();

        filenames.stream()
                .forEach(filename -> executor.submit(() -> computeOccurrences(filename, occurrences)));

        try {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        occurrences.forEach((c, words) -> {
            System.out.print(c + ": ");
            words.forEach(word -> System.out.print(word + ", "));
            System.out.println();
        });
    }

    // Which executor runs faster? 
    // On my PC they wrong at about the same speed. 
    // Can you explain why?
    // They run at around the same speed because the problem we are solving is not a large enough
    // example. On my PC I have 8 cores, so the fact that the cachedThreadPool executor starts 10 threads
    // doesn't really add too much thread contention and therefore we can't see much difference between this
    // and using 8 threads in the FixedThreadPool. For a task like this where we compute things over text
    // it is preferable to stick to around the same amount of threads as we have cores. 
    // So if we expanded the list of text files used in this problem we might start to see
    // that the fixedThreadPool performs better, however we do still have a problem in computeOccurences
    // that might not make fixedThreadPool faster. We don't read the files into memory until computeOccurences
    // is called so each thread needs to retrieve the text from the file. File reading
    // can't be done concurrently so we might need to change even more if we want to use this solution
    // for a larger amount of files. 
    // (If you are curious as to why file reading can't be done concurrently I encurage you to either
    //  have a look on google or wait till you have computer architecture where you learn this anyways.
    //  Why file reading can't be done concurrently isn't part of the syllabus so as long as you know
    //  that it is a problem for concurrency you won't have to worry about why yet.)
    
    
    private static void computeOccurrences(String filename, Map<Character, Set<String>> occurrences) {
        try {
            Files.lines(Paths.get(filename))
                    .flatMap(Words::extractWords)
                    .forEach(word -> {
                        occurrences.compute(word.charAt(0), (unusedKey, set) -> {
                            if (set == null) {
                                set = new HashSet<>();
                            }
                            set.add(word);
                            return set;
                        });
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
