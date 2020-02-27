package week9.inClass;

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
        Path path = Paths.get("text.txt");
        int result = Files.lines(path).mapToInt(s -> {
            int count = 0;
            for(char c : s.toCharArray()) {
                if(c == 'C')
                    count++;
            }
            return count;
        }).sum();
        
        System.out.println("Number of \"C\" in the file is: " + result);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
