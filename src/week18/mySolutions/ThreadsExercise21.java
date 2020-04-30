package week18.mySolutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

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
                            .anyMatch(file -> {
                                try {
                                    return Files.lines(file).count() > 10;
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    return false;
                                }
                            });
            System.out.println(found);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
