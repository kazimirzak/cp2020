package week8;

/**
 * An immutable object representing a box with a single item.
 * @author Kenny Brink - Kebri18@student.sdu.dk
 * @param <T> the type of the content within the box.
 */
public class Box<T> {

    private final T content;
    
    /**
     * Constructs this box.
     * @param content The content to put inside the box.
     */
    
    public Box(T content) {
        this.content = content;
    }
    
    /**
     * Getter for the content.
     * @return the content within this box.
     */

    public T content() {
        return content;
    }
}
