package week9.inClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        Path path = Paths.get("text.txt");
        Files.lines(path)
                .filter(s -> s.startsWith("C"))
                .collect(() -> new ArrayList(), 
                        (list, line) -> list.add(line), 
                        (list1, list2) -> list1.addAll(list2))
                .forEach(System.out::println);
        
        Files.lines(path)
                .filter(s -> s.startsWith("C"))
                .collect(ArrayList::new, 
                        ArrayList::add, 
                        ArrayList::addAll);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
