package week18.inClass;

import Week12.mySolution.Words;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */
public class ThreadsExercise21 {

    /*
    Modify Threads/cp/WalkParallelStreamFindAny such that it returns a boolean
    telling whether there exists at least one file with more than 10 lines.
     */
    public static void main() {
        try {
            boolean found
                    = Files
                            .walk(Paths.get("data"))
                            .filter(Files::isRegularFile)
                            .collect(Collectors.toList())
                            .parallelStream()
                            .anyMatch(path -> {
                                int counter = 0;
                                try {
                                    Iterator<String> iterator = Files.lines(path).iterator();
                                    while(counter <= 10 && iterator.hasNext()) {
                                        iterator.next();
                                        counter++;
                                    }
                                    return counter > 10;
                                } catch (IOException ex) {
                                    return false;
                                 }
                            });
            System.out.println(found);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
