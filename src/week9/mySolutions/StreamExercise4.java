package week9.mySolutions;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class StreamExercise4 {

    /*
    - Create a stream of lines for the file created in StreamExercise1.
    - Use Stream::mapToInt and IntStream::sum to count how many times
      the letter "C" occurs in the entire file.
     */
    public static void main() throws IOException {
        // Get the path to the text file.
        Path path = Paths.get("text.txt");
        
        // Create a stream using lines.
        // Map the elements to an int stream.
        // Where the mapping is done by taking each line
        // Take the length of the lines and take away the length
        // of the same line where each "C" was removed.
        // We then use the method .sum() on the intStream
        // in order to get the total number of "C"'s in all lines
        // in the file.
        int count = Files.lines(path)
                .mapToInt(s -> s.length() - s.replaceAll("C", "").length())
                .sum();
        System.out.println("The number of occurences of \"C\": " + count);
    }
}
