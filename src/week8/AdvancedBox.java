package week8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 * @param <T>
 */
public class AdvancedBox<T> {
    
    private final T content;

    public AdvancedBox(T content) {
        if(content == null)
            throw new IllegalArgumentException("Object can't be null!");
        this.content = content;
    }

    public T content() {
        return content;
    }
    
    public <O> O apply(BoxFunction<T, O> boxFunction) {
        return boxFunction.apply(content);
    }
    
    public static <T, O> List<O> applyToAll(List<AdvancedBox<T>> list, BoxFunction<T, O> boxFunction) {
        return null;
    }
}
