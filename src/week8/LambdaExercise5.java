package week8;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fabrizio Montesi
 */
public class LambdaExercise5 {
    /*
	- Write a static method Box::applyToAll that, given
	  a list of Box(es) with the same type and a BoxFunction with compatible type,
	  applies the BoxFunction to all the boxes and returns a list
	  that contains the result of each BoxFunction invocation.
     */
    public static void main() {
        // Here we make use of the apply to all function.
        // We take a list of boxes that all contain some string.
        // We then use the applyToAll to replace all "i" in the strings
        // to "a".
        List<AdvancedBox<String>> list = createList();
        list.forEach(box -> System.out.println(box.content()));
        System.out.println();
        List<String> result = AdvancedBox.applyToAll(list, (String input) -> input.replaceAll("i", "a"));
        result.forEach(System.out::println);
    }
    
    /**
     * Simple method that returns a list of boxes that all contain strings.
     * @return the list containing the boxes.
     */
    
    public static List<AdvancedBox<String>> createList() {
        List<AdvancedBox<String>> list = new ArrayList<>();
        list.add(new AdvancedBox<>("Hi"));
        list.add(new AdvancedBox<>("There"));
        list.add(new AdvancedBox<>("Lamda"));
        list.add(new AdvancedBox<>("Expressions"));
        list.add(new AdvancedBox<>("Are"));
        list.add(new AdvancedBox<>("Confusing"));
        list.add(new AdvancedBox<>("At"));
        list.add(new AdvancedBox<>("First"));
        return list;
    }
}
