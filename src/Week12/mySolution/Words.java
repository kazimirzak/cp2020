package Week12.mySolution;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */
public class Words {
    
    /*
    * Method taken from Fabrizio, created in his online lecture.
    */
    public static Stream< String> extractWords(String s) {
        List< String> words = new ArrayList<>();

        BreakIterator it = BreakIterator.getWordInstance();
        it.setText(s);

        int start = it.first();
        int end = it.next();
        while (end != BreakIterator.DONE) {
            String word = s.substring(start, end);
            if (Character.isLetterOrDigit(word.charAt(0))) {
                words.add(word);
            }
            start = end;
            end = it.next();
        }

        return words.stream();
    }
}
