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
        // we then create an annomous method that has a variable
        // called result that works as an accumulator.
        // We then take each string in the list and add the
        // length of that string to the result.
        List<String> list = createList();
        AdvancedBox<List<String>> box = new AdvancedBox<>(list);
        long sum = box.apply(l -> {
            long result = 0;
            for(String s : l)
                result += s.length();
            return result;
        });
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
