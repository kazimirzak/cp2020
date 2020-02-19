package week8;

/**
 *
 * @author Fabrizio Montesi
 */
public class LambdaExercise1 {

    /*
	Create a class Box<T> with a single final field of type T called "content".
	Its constructor must take the content as parameter and set it.
	
	Add a public method called "content()" that returns the content of the box.
     */
    public static void main() {
        // Creating a simple box with a string inside
        Box<String> box = new Box<>("This is a box with stuffs in it");
        // Retrieve the content and print it to System out.
        System.out.println(box.content());
    }
}
