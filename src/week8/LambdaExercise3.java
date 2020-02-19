package week8;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fabrizio Montesi
 */
public class LambdaExercise3 {

     /*
	NOTE: When I write Class::methodName, I don't mean to use a method reference (lambda expression), I'm simply
	talking about a particular method.
     */

     /*
	- Create a Box that contains an ArrayList<String> with some elements of your preference.
	- Now compute a sorted version of your list by invoking Box::apply, passing a lambda expression that uses List::sort.
     */
    public static void main() {
        // Creates an advanced box that stores a list of strings.
        // We then use a box function in order to sort the list.
        List<String> list = createList();
        AdvancedBox<List<String>> box = new AdvancedBox<>(list);
        System.out.println(list);
        box.apply(new BoxFunction<List<String>, List<String>>() {
            @Override
            public List<String> apply(List<String> input) {
                // In order to sort we need a comparator.
                // A comparator uses two elements of the list
                // it then needs a method compare that defines
                // how to compare the two elements. In this case
                // since its strings we can use the method compareTo.
                input.sort((s1, s2) -> s1.compareTo(s2));
                return input;
            }
        });
        System.out.println(list);
        
        // This is equivalent to the example above, uses a lambda expression instead.
        list = createList();
        box = new AdvancedBox<>(list);
        box.apply((l) -> {
            l.sort((s1, s2) -> s1.compareTo(s2));
            return l;
        });
        System.out.println(list);

    }
    
    /**
     * Simple method that returns a list with some predefined strings.
     * @return list containing strings.
     */

    public static List<String> createList() {
        List<String> list = new ArrayList<>();
        list.add("Hi");
        list.add("There");
        list.add("My");
        list.add("IQ");
        list.add("Is");
        list.add("42");
        return list;
    }
}
