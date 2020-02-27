package week9.mySolutions;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class StreamExercise5 {
    /* ! (Exercises marked with ! are more difficult.)
    - Create a stream of lines for the file created in StreamExercise1.
    - Use Stream::map to map each line to a HashMap<String, Integer> that
      stores how many times each character appears in the line.
      For example, for the line "abbc" you would produce a map with entries:
        a -> 1
        b -> 2
        c -> 1
    - Use Stream::reduce(T identity, BinaryOperator<T> accumulator)
      to produce a single HashMap<String, Integer> that stores
      the results for the entire file.
    */
    
    public static void main() throws IOException {
        // Get the path to the file.
        Path path = Paths.get("text.txt");
        
        // Get a stream with all lines in text file
        // using lines.
        Map<Character, Integer> m = Files.lines(path).map(s -> {
            // First we map each string in the stream to the map.
            // This is done by creating a new map. We then
            // go through the string character by character.
            // If the map already contains the character
            // we add 1 to the current value
            // If not we put the character in there
            // and the value for that starts on 1.
            Map<Character, Integer> map = new HashMap<>();
            for (char c : s.toCharArray()) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
            return map;
        }).reduce(new HashMap<>(), (map1, map2) -> {
            // We then want to reduce the stream down to a single object.
            // This is done with reduce. 
            // The first argument is the identity, i.e. 
            // What does it start out with/what to do if the stream is empty.
            // The second argument is a function that can accumulate 
            // Two elements in the stream -> take 2 objects and turn them into 1.
            // This is simply done by taking the first map, put that into a new map
            // then for each key in the other map we check if they exist in the map
            // if it does we add the value in the other map to the result
            // if not we add it to the result as a new entry.
            Map<Character, Integer> result = new HashMap<>(map1);
            map2.forEach((c, value) -> {
                if(result.containsKey(c)) {
                    result.put(c, result.get(c) + value);
                } else {
                    result.put(c, value);
                }
            });
            return result;
        });
        
        // We then just print the content of the map.
        m.forEach((c, value) -> System.out.println(c + "->" + value));
    }

}
