package week8;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 * @param <I>
 * @param <O>
 */
public interface BoxFunction<I,O> {
    
    public O apply(I input);
}
