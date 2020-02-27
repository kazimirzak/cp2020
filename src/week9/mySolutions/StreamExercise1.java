package week9.mySolutions;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class StreamExercise1 {

    /*
    - Create a file with many (>100) lines of text.
      For example, you can use this website: loremipsum.io
    - Use Files.lines to get a stream of the lines contained within the file.
    - Use Stream::filter and Stream::forEach to print on screen each line that ends with a dot.
     */
    public static void main() throws IOException {
        // We get the file with all the text.
        Path path = Paths.get("text.txt");

        // We then call on Files.lines to get a stream of all lines in the file.
        // We then filter each line using a predicate. A predicate is essentially
        // a method that returns true/false. If the stream uses the method
        // and it returns true, that element will be in the resulting stream.
        // We then use foreach to print all the lines that are left in the stream
        // to system.out.
        Files.lines(path).filter(s -> s.endsWith(".")).forEach(System.out::println);

        // Uses Files.lines and calls count on that stream to count
        // how many lines are in the file.
        System.out.println("Number of lines in file: " + Files.lines(path).count());

        // Here we do the same as above, however we first filter the ones 
        // that end with "." and count those.
        long count = Files.lines(path).filter(s -> s.endsWith(".")).count();
        System.out.println("Number of lines that ends with a \".\": " + count);

    }
}
