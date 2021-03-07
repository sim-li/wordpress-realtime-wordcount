package xyz.lischka.textanalysis

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import xyz.lischka.textanalysis.services.ProcessingUtil.countWordsInBlogPost
import xyz.lischka.textanalysis.entities.events.NewBlogPostPublished
import xyz.lischka.textanalysis.entities.WordCount
import xyz.lischka.textanalysis.entities.Words
import xyz.lischka.textanalysis.services.ProcessingUtil

class ProcessingUtilTest {
    @Nested
    inner class processing_pipeline_should() {
        @Test
        fun `count from well formed html input`() {
            val inp = NewBlogPostPublished(id="xyz", htmlContent="<h1>Auch ohne Grundschul-Empfehlung in Bayern aufs Gymnasium</h1>\n\n" +
                    "<p>Kein anderes Bundesland Bayern macht es Kindern so schwer, von</p>")

            val actual =  countWordsInBlogPost(inp)

            assertThat(actual.counts).isEqualTo(mapOf(
                "Auch" to 1,
                "Bayern" to 2,
                "Bundesland" to 1,
                "Grundschul-Empfehlung" to 1,
                "Gymnasium" to 1,
                "Kein" to 1,
                "Kindern" to 1,
                "anderes" to 1,
                "aufs" to 1,
                "es" to 1,
                "in" to 1,
                "macht" to 1,
                "ohne" to 1,
                "schwer," to 1,
                "so" to 1,
                "von" to 1
            ))
        }

        @Test
        fun `count from malformed html input`() {
            val inp = NewBlogPostPublished(id="xyz", htmlContent="<h1>Auch ohne Grundschul-Empfehlung in Bayern aufs Gymnasium</h1>\n\n" +
                    "<p>Kein anderes Bundesland Bayern macht es Kindern so schwer, von")

            val actual =  countWordsInBlogPost(inp)

            assertThat(actual.counts).isEqualTo(mapOf(
                "Auch" to 1,
                "Bayern" to 2,
                "Bundesland" to 1,
                "Grundschul-Empfehlung" to 1,
                "Gymnasium" to 1,
                "Kein" to 1,
                "Kindern" to 1,
                "anderes" to 1,
                "aufs" to 1,
                "es" to 1,
                "in" to 1,
                "macht" to 1,
                "ohne" to 1,
                "schwer," to 1,
                "so" to 1,
                "von" to 1
            ))
        }

        @Test
        fun `count from text input`() {
            val inp = NewBlogPostPublished(id="xyz", htmlContent="Auch ohne ohne Bayern")

            val actual =  countWordsInBlogPost(inp)

            assertThat(actual.counts).isEqualTo(mapOf(
                "Auch" to 1,
                "ohne" to 2,
                "Bayern" to 1
            ))
        }

        @Test
        fun `count from empty input`() {
            val inp = NewBlogPostPublished(id="xyz", htmlContent="")

            val actual =  countWordsInBlogPost(inp)

            assertThat(actual.counts).isEqualTo(emptyMap<String, Int>())
        }
    }

    @Nested
    inner class word_count_should() {
        @Test
        fun `count a word that occurs only once`() {
            val words = Words(listOf("hi"))

            val actual: WordCount = ProcessingUtil.countWords(words)

            assertThat(actual.count).isEqualTo(mapOf("hi" to 1))
        }

        @Test
        fun `count a word with multiple occurrences`() {
            val words = Words(listOf("bye", "bye"))

            val actual: WordCount = ProcessingUtil.countWords(words)

            assertThat(actual.count).isEqualTo(mapOf("bye" to 2))
        }

        @Test
        fun `count a single occurring word and another one with multiple occurrences`() {
            val words = Words(listOf("hi", "how", "bye", "do", "bye", "you", "bye"))

            val actual: WordCount = ProcessingUtil.countWords(words)

            assertThat(actual.count).isEqualTo(mapOf(
                "hi" to 1,
                "bye" to 3,
                "how" to 1,
                "do" to 1,
                "you" to 1
            ))
        }

        @Test
        fun `process empty list of words`() {
            val words = Words(listOf())

            val actual: WordCount = ProcessingUtil.countWords(words)

            assertThat(actual.count).isEqualTo(emptyMap<String, Int>())
        }
    }

    @Nested
    inner class split_words_should() {
        @Test
        fun `split several words delimited by space`() {
            val words = "hello having fun"

            val actual: Words = ProcessingUtil.splitTextToWords(words)

            assertThat(actual.words).isEqualTo(listOf("hello", "having", "fun"))
        }

        @Test
        fun `ignore empty string`() {
            val words = ""

            val actual: Words = ProcessingUtil.splitTextToWords(words)

            assertThat(actual.words).isEqualTo(emptyList<String>())
        }
    }

    @Nested
    inner class strip_html_should() {
        @Test
        fun `strip a a bold opening and closing tag`() {
            val html = "<b>Hello</b> world"

            val actual = ProcessingUtil.stripHtml(html)

            assertThat(actual).isEqualTo("Hello world")
        }

        @Test
        fun `strip line breaks`() {
            val html = "<b>Hello</b>\n\nworld"

            val actual = ProcessingUtil.stripHtml(html)

            assertThat(actual).isEqualTo("Hello world")
        }

        @Test
        fun `strip nested tags with linebreaks`() {
            val html = "<html><p><b>Hello</b>\n\nworld</p>\n</html>"

            val actual = ProcessingUtil.stripHtml(html)

            assertThat(actual).isEqualTo("Hello world")
        }
    }
}
