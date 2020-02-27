package week9.inClass;

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
        Path path = Paths.get("text.txt");
        Files.lines(path).map(s -> {
            Map<Character, Integer> map = new HashMap<>();
            for(char c : s.toCharArray()) {
                if(map.containsKey(c)) {
                    map.put(c, map.get(c) +1 );
                } else {
                    map.put(c, 1);
                }
            }
            return map;
        }).reduce(new HashMap<>(), ((map1, map2) -> {
            Map<Character, Integer> result = new HashMap<>(map1);
            map2.forEach((c, count) -> {
                if(result.containsKey(c)) {
                    result.put(c, result.get(c) + count);
                } else {
                    result.put(c, count);
                }
            });
            return result;
        })).forEach((key, value) -> System.out.println(key + "->" + value));

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
