package week8;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fabrizio Montesi
 */
public class LambdaExercise4 {
    /*
	- Create a list of type ArrayList<String> with some elements of your preference.
	- Create a Box that contains the list.
	- Now compute the sum of the lengths of all strings in the list inside of the box,
	  by invoking Box::apply with a lambda expression.
     */
    public static void main() {
        // Takes a list of strings and puts them inside a box.
        // We then use a box function that takes the list,
        // converts it to a stream. It will then map this stream
        // To a stream of ints by calling String.length()
        // We then sum to integer stream.
        List<String> list = createList();
        AdvancedBox<List<String>> box = new AdvancedBox<>(list);
        long sum = box.apply(l -> l.stream().mapToInt(String::length).sum());
        System.out.println(sum);
    }
    
    /**
     * Method that returns a list of predetermined strings.
     * @return the list of strings.
     */

    public static List<String> createList() {
        List<String> list = new ArrayList<>();
        list.add("Hi");
        list.add("There");
        list.add("Lamda");
        list.add("Expressions");
        list.add("Are");
        list.add("Confusing");
        list.add("At");
        list.add("First");
        return list;
    }
}
