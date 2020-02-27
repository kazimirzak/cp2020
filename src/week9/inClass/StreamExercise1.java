package week9.inClass;

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
        Path path = Paths.get("text.txt");
        Files.lines(path)
                .filter(s -> s.endsWith("."))
                .forEach(System.out::println);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
