package week8;

import java.util.ArrayList;
import java.util.List;
import week8.AdvancedBox;
import week8.BoxFunction;

/**
 * 
 * @author Fabrizio Montesi
 */
public class LambdaExercise3
{
	/*
	NOTE: When I write Class::methodName, I don't mean to use a method reference (lambda expression), I'm simply
	talking about a particular method.
	*/
	
	/*
	- Create a Box that contains an ArrayList<String> with some elements of your preference.
	- Now compute a sorted version of your list by invoking Box::apply, passing a lambda expression that uses List::sort.
	*/
    public static void main() {
        List<String> list = createList();
        AdvancedBox<List<String>> box = new AdvancedBox<>(list);
        box.apply(new BoxFunction<List<String>, List<String>>() {
           @Override
           public List<String> apply(List<String> input) {
               input.sort((s1, s2) -> s1.compareTo(s2));
               return input;
           }
        });
        System.out.println(list);
        
        list = createList();
        box = new AdvancedBox<>(list);
        box.apply((l) -> {
            l.sort((s1, s2) -> s1.compareTo(s2));
            return l;
        });
        System.out.println(list);
        
    }
    
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
