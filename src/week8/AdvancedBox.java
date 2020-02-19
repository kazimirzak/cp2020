package week8;

import java.util.ArrayList;
import java.util.List;

/**
 * An immutable class advanced box. Used to contain a single item.
 * @author Kenny Brink - Kebri18@student.sdu.dk
 * @param <T> The content of the box.
 */
public class AdvancedBox<T> {

    private final T content;

    /**
     * Constructs the box.
     * @param content The content to put in the box.
     * @throws IllegalArgumentException of the content is null.
     */
    public AdvancedBox(T content) {
        if (content == null) {
            throw new IllegalArgumentException("Object can't be null!");
        }
        this.content = content;
    }
    
    /**
     * @return the content within this box.
     */

    public T content() {
        return content;
    }
    
    /**
     * Applies the given boxFunction to this box.
     * @param <O> The return type of the boxFunction.
     * @param boxFunction The actual box function to apply to the box.
     * @return the object created after applying the box function.
     */

    public <O> O apply(BoxFunction<T, O> boxFunction) {
        return boxFunction.apply(content);
    }
    
    /**
     * Static function used to apply a box function to all boxes within a list.
     * @param <I> The type of the content within the box.
     * @param <O> The type of the object after the box function is applied.
     * @param list The list of boxes that the box function should be applied to.
     * @param boxFunction The box function.
     * @return a list of what is returned by the box function when applied to all boxes.
     */

    public static <I, O> List<O> applyToAll(List<AdvancedBox<I>> list, 
            BoxFunction<I, O> boxFunction) {
        // We create a new list that can hold the result of each apply function.
        // We then apply it to each one of them in the list and add them to the new list.
        List<O> result = new ArrayList<>();
        for (AdvancedBox<I> box : list) {
            result.add(box.apply(boxFunction));
        }
        return result;
    }
    
    
    
    
    
    
    
}
