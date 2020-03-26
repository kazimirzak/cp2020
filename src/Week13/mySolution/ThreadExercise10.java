package Week13.mySolution;

import Week12.mySolution.Words;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */
public class ThreadExercise10 {

    /*
    Modify ThreadsExercise9 to use Files.walk over the data directory in the Threads project, such
    that you recursively visit all files in that directory instead of using a fixed list of filenames.
     */
    public static void main() {
        // The directory for where the data folder is.
        String directory = System.getProperty("user.dir") + "/data";
        // We change the map so its from character -> Set<String>
        // Character == the first character of the word.
        // Set<String> == the set of words that start with the given character.
        Map<Character, Set<String>> occurrences = new ConcurrentHashMap<>();
        List<Thread> threads = new ArrayList();
        
        // We use Files.walk to get all files.
        // We then filter out anything that is not a regular file, i.e. directories.
        // We then start a new thread for each path where their only purpose is to compute occurences for that file.
        try {
            Files.walk(Paths.get(directory))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        Thread t = new Thread(() -> {
                            computeOccurrences(path, occurrences);
                        });
                        t.start();
                        threads.add(t);
                    });
            threads.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });
            occurrences.forEach((c, words) -> {
                System.out.print(c + ": ");
                words.forEach(word -> System.out.print(word + ", "));
                System.out.println();
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void computeOccurrences(Path path, Map<Character, Set<String>> occurrences) {
        try {
            Files.lines(path)
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
