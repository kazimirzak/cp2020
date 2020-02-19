package week8;

/**
 *
 * @author Fabrizio Montesi
 */
public class LambdaExercise2 {

    /*
	Let's make a more advanced box.
	
	- Create a new interface BoxFunction<I,O> with a method "apply" that
		takes something of type I (for input) as parameter and has O (for output)
	    as return type.
		
	- Modify the Box class by adding a new method called "apply" that:
		* Takes as parameter a BoxFunction<I,O> that requires as input something
		  of the same type of the content of the box.
		* Has the output type of the BoxFunction parameter as return type.
		* Its implementation applies the BoxFunction to the content of the box
		  and returns the result.

	- Modify the Box class constructor such that it throws an IllegalArgumentException
	  if the passed content is null.
     */
    public static void main() {
        // Creates a box with a string and the uses the apply function to convert it to
        // an integer. In this case its the length of the string.
        Integer number;
        AdvancedBox<String> box = new AdvancedBox("Hey there 42");
        number = box.apply(new BoxFunction<String, Integer>() {

            @Override
            public Integer apply(String s) {
                return s.length();
            }
        });
        
        // This is equivalent to the example above. Uses lambda expression instead.
        System.out.println(number);
        number = 0;
        number = box.apply((input) -> {
            return input.length();
        });
        
        // This is equivalent to the example above. Uses method reference operator instead.
        System.out.println(number);
        number = 0;
        number = box.apply(String::length);
        System.out.println(number);
    }
}
