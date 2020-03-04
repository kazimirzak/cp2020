package week10.inClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadExercise3 {

    /*
    Modify the threads/cp/SynchronizedMap2T example such that:
    - There are now two maps (instead of just one) for accumulating results, one for each thread.
    - Each thread uses only its own map, without synchronizing on it.
    - After the threads terminate, create a new map where you merge the results of the two dedicated maps.
	
    Questions:
    - Does the resulting code work? Can you explain why?
    - Does the resulting code perform better or worse than the original example SynchronizedMap2T?
    - Can you hypothesise why?
     */
    public static void main() {
        Map<String, Integer> results1 = new HashMap<>();
        Map<String, Integer> results2 = new HashMap<>();

        Thread t1 = new Thread(() -> {
            try {
                Files.lines(Paths.get("text1.txt"))
                        .flatMap(s -> Stream.of(s.split(" ")))
                        .forEach(word -> {
                            results1.merge(word, 1, Integer::sum);
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                Files.lines(Paths.get("text2.txt"))
                        .flatMap(s -> Stream.of(s.split(" ")))
                        .forEach(word -> {
                            results2.merge(word, 1, Integer::sum);
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        results2.forEach((key, value) -> {
            results1.merge(key, value, Integer::sum);
        });
        results1.forEach((key, value) -> System.out.println(key + "->" + value));
        
    }
}
