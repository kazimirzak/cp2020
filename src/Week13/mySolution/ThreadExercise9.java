package Week13.mySolution;

import Week12.mySolution.Words;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */
public class ThreadExercise9 {

    /*
    Modify Threads/cp/ConcurrentMap to compute a map of type Map<Character, Set<String>>.
    The map should map a character to the set of words that start with that character (case sensitive).
     */
    public static void main() {
        // We change the map so its from character -> Set<String>
        // Character == the first character of the word.
        // Set<String> == the set of words that start with the given character.
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

        CountDownLatch latch = new CountDownLatch(filenames.size());

        filenames.stream()
                .map(filename -> new Thread(() -> {
            computeOccurrences(filename, occurrences);
            latch.countDown();
        }))
                .forEach(Thread::start);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Small change such that it print 'c': 'word1', 'word2'... etc.
        // Where 'c' is the character and word1, word2 is the words that start with that character.
        occurrences.forEach((c, words) -> {
            System.out.print(c + ": ");
            words.forEach(word -> System.out.print(word + ", "));
            System.out.println();
        });
    }

    private static void computeOccurrences(String filename, Map<Character, Set<String>> occurrences) {
        // Here we modify the method such that it uses compute instead. Here it will take a key and try
        // to compute a new value for that key. This is done by checking if the value for the key is null.
        // If it is we create a new set. If its not we just add it to the set. 
        try {
            Files.lines(Paths.get(filename))
                    .flatMap(Words::extractWords)
                    .forEach(word -> {
                        occurrences.compute(word.charAt(0), (unusedKey, set) -> {
                           if(set == null) {
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
