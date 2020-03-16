package week11.mySolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadExercise6 {

    /*
    This exercise generalises Threads/cp/SharedMap2T.
    Feel free to borrow the appropriate pieces of code from that example.
    
    Implement a method 
    	public static Map< String, Integer > computeOccurrences( Stream< String > filenames )
    that returns a map of how many times each word occurs (as in SharedMap2T) in the files named
    in the stream parameter.
    
    Try first to implement the method sequentially (no threads), then try
    to implement it such that each file is processed by a dedicated thread (all threads
    should run concurrently and be waited for).
     */
    
    public static void main() {
        // Simple test of each method to see how fast they are and their correctness.
        Map<String, Integer> result;
        Stream<String> filesnames = getFiles().stream();
        result = computeOccurences(filesnames);
        //result = computeOccurencesConcurrent(filesnames);
        result.forEach((key, value) -> System.out.println(key + " -> " + value));
        
//        doAndMeasure(() -> {
//            computeOccurences(filesnames);
//        });
//        doAndMeasure(() -> {
//            computeOccurencesConcurrent(filesnames);
//        });
        
    }

    /*
    * Sequential implementation of the compute occurences.
    * It takes all the files in the given stream. Calls on Files.lines on each file
    * It then takes this stream of lines and flatmaps it to each word by using split
    * on each space. After that we just take the resulting stream which is now each word
    * and merge it with the map. 
     */
    private static Map<String, Integer> computeOccurences(Stream<String> filenames) {
        Map<String, Integer> occurences = new HashMap<>();
        filenames.forEach(file -> {
            try {
                Files.lines(Paths.get(file))
                        .flatMap(line -> Stream.of(line.split(" ")))
                        .forEach(word -> occurences.merge(word, 1, Integer::sum));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return occurences;
    }

    /*
    * A concurrent version of the above method. It does exactly the same, however
    * Each file is now allocated to a specific thread. Each thread is added to a list
    * Once we have created all threads we start them all and then wait for them to finish.
    * Could this be upgraded, maybe by using more memory in order to gain speed?
     */
    private static Map<String, Integer> computeOccurencesConcurrent(Stream<String> filenames) {
        Map<String, Integer> occurences = new HashMap<>();
        List<Thread> threads = new ArrayList<>();
        filenames.forEach(file -> {
            Thread t = new Thread(() -> {
                try {
                    Files.lines(Paths.get(file))
                            .flatMap(line -> Stream.of(line.split(" ")))
                            .forEach(word -> {
                                synchronized (occurences) {
                                    occurences.merge(word, 1, Integer::sum);
                                }
                            });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            threads.add(t);
        });
        threads.forEach(t -> t.start());
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        return occurences;
    }
    
    // Fabrizios method for measuring other methods run-time
    public static void doAndMeasure(Runnable runnable) {
        long t1 = System.currentTimeMillis();
        runnable.run();
        long t2 = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (t2 - t1) + "ms");
    }
    
    // Method to get a list of files. Adds 50 of each text1.txt and text2.txt
    public static List<String> getFiles() {
        List<String> files = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            files.add("text1.txt");
            files.add("text2.txt");
        }
        return files;
    }
}
