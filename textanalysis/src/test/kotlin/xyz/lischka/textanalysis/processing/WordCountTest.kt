package xyz.lischka.textanalysis.processing

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class WordCountAlgorithmTest {
    @Nested
    inner class word_count_should() {
        @Test
        fun `count a word that occurs only once`() {
            val words = listOf("hi")

            val actual: Map<String, Int> = WordCount.count(words)

            assertThat(actual).isEqualTo(mapOf("hi" to 1))
        }

        @Test
        fun `count a word with multiple occurrences`() {
            val words = listOf("bye", "bye")

            val actual: Map<String, Int> = WordCount.count(words)

            assertThat(actual).isEqualTo(mapOf("bye" to 2))
        }

        @Test
        fun `count a single occurring word and another one with multiple occurrences`() {
            val words = listOf("hi", "how", "bye", "do", "bye", "you", "bye")

            val actual: Map<String, Int> = WordCount.count(words)

            assertThat(actual).isEqualTo(mapOf(
                "hi" to 1,
                "bye" to 3,
                "how" to 1,
                "do" to 1,
                "you" to 1
            ))
        }

        @Test
        fun `process empty list of words`() {
            val words = listOf<String>()

            val actual: Map<String, Int> = WordCount.count(words)

            assertThat(actual).isEqualTo(emptyMap<String, Int>())
        }
    }
}
