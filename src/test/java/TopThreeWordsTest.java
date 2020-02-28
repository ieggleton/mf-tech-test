import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Given: A Top Three Word Parser")
public class TopThreeWordsTest {

    @Nested
    @DisplayName("When: An empty string is parsed")
    public class EmptyList {

        @Test
        @DisplayName("Then: Nothing is returned")
        public void testEmptyResult() {
            assertThat(TopThreeWords.top_3_words("")).isEmpty();
        }
    }

    @Nested
    @DisplayName("When: a string with less than three repeated words is parsed")
    public class LessThenThreeRepeated {

        @Test
        @DisplayName("Then: Those items are returned")
        public void testLessThanThreeRepeatedWords() {
            assertThat(TopThreeWords.top_3_words(" //wont won't won't"))
                    .containsOnly("wont", "won't");
        }
    }

    @Nested
    @DisplayName("When: A string with punctuation and several repeated words is parsed")
    public class PunctuationAndRepeatedWords {

        @Test
        @DisplayName("Then:The top three words are returned")
        public void testTopThreeRepeatedWords() {
            assertThat(TopThreeWords.top_3_words("e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e"))
                    .containsOnly("e", "ddd", "aa");
        }
    }

    @Nested
    @DisplayName("When: A complex string is parsed")
    public class ComplexString {

        @Test
        @DisplayName("Then: The top three words are returned")
        public void testTopThreeRepeatedWords() {
            assertThat(TopThreeWords.top_3_words("In a village of La Mancha, the name of which I have\n"
                    + "no desire to call to\n"
                    + "mind, there lived not long since one of those gentlemen that keep a lance\n"
                    + "in the lance-rack, an old buckler, a lean hack, and a greyhound for\n"
                    + "coursing. An olla of rather more beef than mutton, a salad on most\n"
                    + "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra\n"
                    + "on Sundays, made away with three-quarters of his income."))
                    .containsOnly("a", "of", "on");
        }
    }

    @Nested
    @DisplayName("When: Text from a stream is parsed")
    public class TextFromStream {

        @Test
        @DisplayName("Then: The top three words are returned")
        public void testTopThreeWords() throws IOException {
            try (InputStream input = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("test_text")) {
                assertThat(TopThreeWords.top_3_words_large(input))
                        .containsOnly("a", "of", "on");
            }
        }
    }

}
