package xyz.lischka.bff

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import xyz.lischka.bff.entities.events.WordCountAnalysisResult
import xyz.lischka.bff.infrastructure.repositories.WordCountAnalysisResultRepository
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@SpringBootTest
class WordCountAnalysisResultRepositoryTest {
    @Autowired
    private lateinit var repo: WordCountAnalysisResultRepository

    @Nested
    inner class blogpost_repo_should() {
        @BeforeEach
        fun init() {
            repo.clear()
        }

        @Test
        fun `save single analysis result`() {
            repo.save(
                WordCountAnalysisResult(
                    "some-id",
                    htmlContent =  "<b>some content</b>\n\n",
                    counts = mapOf("some" to 1, "content" to 1),
                    date = LocalDateTime.parse("2021-02-02T18:44:55")
                )
            )

            val actual = repo.getAll()

            assertThat(actual).isEqualTo(listOf(
                WordCountAnalysisResult(
                    "some-id",
                    htmlContent =  "<b>some content</b>\n\n",
                    counts = mapOf("some" to 1, "content" to 1),
                    date = LocalDateTime.parse("2021-02-02T18:44:55")
                )
            )
            )
        }

        @Test
        fun `save multiple analysis results`() {
            repo.save(
                WordCountAnalysisResult(
                    "some-id",
                    htmlContent =  "<b>some content</b>\n\n",
                    counts = mapOf("some" to 1, "content" to 1),
                    date = LocalDateTime.parse("2021-02-02T18:44:55")
                )
            )
            repo.save(listOf(
                WordCountAnalysisResult(
                    "another-id",
                    htmlContent =  "<b>some content</b>\n\n",
                    counts = mapOf("some" to 1, "content" to 1),
                    date = LocalDateTime.parse("2021-02-02T18:44:56")
                ),
                WordCountAnalysisResult(
                    "jet-another-id",
                    htmlContent =  "<b>some content</b>\n\n",
                    counts = mapOf("some" to 1, "content" to 1),
                    date = LocalDateTime.parse("2021-02-02T18:44:57")
                )
            ))

            val actual = repo.getAll()

            assertThat(actual).isEqualTo(listOf(
                WordCountAnalysisResult(
                    "some-id",
                    htmlContent =  "<b>some content</b>\n\n",
                    counts = mapOf("some" to 1, "content" to 1),
                    date = LocalDateTime.parse("2021-02-02T18:44:55")
                ),
                WordCountAnalysisResult(
                    "another-id",
                    htmlContent =  "<b>some content</b>\n\n",
                    counts = mapOf("some" to 1, "content" to 1),
                    date = LocalDateTime.parse("2021-02-02T18:44:56")
                ),
                WordCountAnalysisResult(
                    "jet-another-id",
                    htmlContent =  "<b>some content</b>\n\n",
                    counts = mapOf("some" to 1, "content" to 1),
                    date = LocalDateTime.parse("2021-02-02T18:44:57")
                )
            ))
        }
    }
}
