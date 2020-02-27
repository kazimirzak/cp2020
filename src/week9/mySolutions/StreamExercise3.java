package week9.mySolutions;



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
        // Get the file with text.
        Path path = Paths.get("text.txt");

        // Use lines to create stream of the text.
        // We then filter all the lines that contain "L"
        // We then use count to count the number of elements in the stream.
        long count = Files.lines(path).filter(s -> s.contains("L")).count();
        System.out.println("The number of lines that contains \"L\": " + count);
    }
}
