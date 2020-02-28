import java.util.Comparator;

/**
 * Wrapper for string and number of occurrences to do a simple search
 * later on as defined by the comparator
 *
 * Would use Lombok but no static libraries :)
 */
public class WordCount {

    public static final Comparator<WordCount> SORT_DESC =
            Comparator.comparingInt(wc -> wc.count * -1);

    private final String word;
    private int count;

    public WordCount(String word) {
        this.word = word;
        count = 1;
    }

    public WordCount newOccurrence() {
        count++;
        return this;
    }

    public String getWord() {
        return word;
    }
}
