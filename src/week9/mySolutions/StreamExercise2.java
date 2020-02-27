package week9.mySolutions;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class StreamExercise2 {

    /*
    - Create a stream of lines for the file created in StreamExercise1.
    - Use Stream::filter and Stream::collector (the one with three parameters)
      to create an ArrayList of all lines that start with a "C".
    - Suggestion: look at https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.function.Supplier-java.util.function.BiConsumer-java.util.function.BiConsumer-
     */
    public static void main() throws IOException {
        // Get path for text file.
        Path path = Paths.get("text.txt");

        // We create a stream using lines again.
        // We then filter all the lines that start with "C"
        // We then use .collct.
        // What collect needs is 3 arguments.
        // The first is how should it start the collection
        // in this case we create an empty arraylist.
        // The next argument is how does it add an element to
        // the list.
        // The last argument is how does it combine two different elements after
        // they have been added to a list.
        List<String> lines = Files.lines(path)
                .filter(s -> s.startsWith("C"))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        // Print each line in the list to System.out
        lines.forEach(System.out::println);

    }
}
