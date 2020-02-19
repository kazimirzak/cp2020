package week8;

/**
 * A single method interface.
 * @author Kenny Brink - Kebri18@student.sdu.dk
 * @param <I> The input type
 * @param <O> The output type
 */
public interface BoxFunction<I, O> {
    
    /**
     * Will apply a defined algorithm to the given input that transforms it to the type O.
     * @param input The input to transform.
     * @return The input after it has been transformed.
     */
    
    public O apply(I input);
}
