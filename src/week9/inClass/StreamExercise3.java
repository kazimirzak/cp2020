package week9.inClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class StreamExercise3 {
    
    /*
    - Create a stream of lines for the file created in StreamExercise1.
    - Use Stream::filter and Stream::count to count how many lines
      contain the letter "L".
    */
    public static void main() throws IOException {
        Path path = Paths.get("text.txt");
        long count = Files.lines(path).filter(s -> s.contains("L")).count();
        System.out.println("Number of lines with \"L\": " + count);
    }
}
