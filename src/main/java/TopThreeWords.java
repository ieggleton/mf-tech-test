import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class TopThreeWords {

    private TopThreeWords() {
        // effectively a utils class
    }

    /**
     * Regex for the splitting of words using the following as terminators...
     * - punctuation
     * - whitespace
     * - newline
     * - NOT apostrophes
     */
    private static final Pattern WORD_SPLITTER =
            Pattern.compile("[\\pP\\s$&&[^']]+", Pattern.MULTILINE);
    /**
     * Matches words
     */
    private static final Pattern WORD_MATCHER =
            Pattern.compile("[a-zA-Z']+", Pattern.MULTILINE);

    /**
     * Take a string and turn it into a list of the 3 most common words
     *
     * @param toParse The string from which to get the 3 top words (not not allowed)
     * @return An array containing the top 3 words in lower case or less if none present
     */
    public static String[] top_3_words(final String toParse) {
        final var words = WORD_SPLITTER.split(toParse);
        return getWordCounts(Arrays.stream(words));
    }

    /**
     * Take an input stream and parse for the top 3 words - for use with large bits of
     * text
     *
     * @param toParse The input stream to parse
     * @return The tip 3 repeated words
     */
    public static String[] top_3_words_large(final InputStream toParse) {
        return getWordCounts(
                new Scanner(toParse).findAll(WORD_MATCHER)
                        .map(MatchResult::group)
        );
    }

    private static String[] getWordCounts(Stream<String> wordStream) {
        final var wordCounts = new HashMap<String, WordCount>();
        wordStream
                .filter(not(String::isBlank))
                .map(String::toLowerCase)
                .forEach(word -> {
                    // gone for readability over possible efficiency here
                    wordCounts.computeIfPresent(word, (s, wordCount) -> wordCount.newOccurrence());
                    wordCounts.computeIfAbsent(word, WordCount::new);
                });

        final var wordCountAsList = new ArrayList<>(wordCounts.values());
        wordCountAsList.sort(WordCount.SORT_DESC);

        return wordCountAsList.stream()
                .limit(3)
                .map(WordCount::getWord)
                .toArray(String[]::new);
    }
}
