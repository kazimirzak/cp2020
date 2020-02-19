package week8;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 * @param <T>
 */
public class Box<T> {

    private final T content;

    public Box(T content) {
        this.content = content;
    }

    public T content() {
        return content;
    }
}
