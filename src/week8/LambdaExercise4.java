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
        List<String> list = createList();
        AdvancedBox<List<String>> box = new AdvancedBox<>(list);
        long sum = box.apply(l -> l.stream().mapToInt(String::length).sum());
        System.out.println(sum);
    }

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
