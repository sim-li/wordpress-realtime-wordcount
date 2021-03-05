package xyz.lischka.scraping.infrastructure.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import xyz.lischka.scraping.entities.rest.BlogPost
import xyz.lischka.scraping.entities.rest.BlogPostContent
import xyz.lischka.scraping.infrastructure.repositories.BlogPostRepository
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@SpringBootTest
class BlogPostRepositoryTest {
    @Autowired
    private lateinit var repo: BlogPostRepository

    @Nested
    inner class blogpost_repo_should() {
        @BeforeEach
        fun init() {
            repo.clear()
        }

        @Test
        fun `save single blog post`() {
            repo.save(
                BlogPost(
                    "some-id",
                    LocalDateTime.parse("2021-02-02T18:44:55"),
                    BlogPostContent(
                        rendered = "<b>some content</b>\n\n"
                    )
                )
            )

            val actual = repo.getAll()

            assertThat(actual).isEqualTo(listOf(
                BlogPost(
                    "some-id",
                    LocalDateTime.parse("2021-02-02T18:44:55"),
                    BlogPostContent(
                        rendered = "<b>some content</b>\n\n"
                    )
                ))
            )
        }

        @Test
        fun `save multiple blog posts`() {
            repo.save(
                BlogPost(
                    "abc",
                    LocalDateTime.parse("2021-02-02T16:44:55"),
                    BlogPostContent(
                        rendered = "<b>first content</b>\n\n"
                    )
                )
            )
            repo.save(listOf(
                BlogPost(
                    "some-id",
                    LocalDateTime.parse("2021-02-02T18:44:55"),
                    BlogPostContent(rendered = "<b>some content</b>\n\n")
                ),
                BlogPost(
                    "another-id",
                    LocalDateTime.parse("2021-02-02T19:44:55"),
                    BlogPostContent(rendered = "<b>more content</b>\n\n")
                )
             )
            )

            val actual = repo.getAll()

            assertThat(actual).isEqualTo(listOf(
                BlogPost(
                    "abc",
                    LocalDateTime.parse("2021-02-02T16:44:55"),
                    BlogPostContent(
                        rendered = "<b>first content</b>\n\n"
                    )
                ),
                BlogPost(
                    "some-id",
                    LocalDateTime.parse("2021-02-02T18:44:55"),
                    BlogPostContent(rendered = "<b>some content</b>\n\n")
                ),
                BlogPost(
                    "another-id",
                    LocalDateTime.parse("2021-02-02T19:44:55"),
                    BlogPostContent(rendered = "<b>more content</b>\n\n")
                ))
            )
        }

        @Test
        fun `gets newest date from all entries`() {
            repo.save(listOf(
                BlogPost(
                    "abc",
                    LocalDateTime.parse("2021-02-02T16:44:55"),
                    BlogPostContent(
                        rendered = "<b>first content</b>\n\n"
                    )
                ),
                BlogPost(
                    "some-id",
                    LocalDateTime.parse("2021-03-02T12:44:55"),
                    BlogPostContent(rendered = "<b>some content</b>\n\n")
                ),
                BlogPost(
                    "another-id",
                    LocalDateTime.parse("2021-02-02T19:44:55"),
                    BlogPostContent(rendered = "<b>more content</b>\n\n")
                )
            )
            )

            val actual = repo.getNewestDate()

            assertThat(actual).isEqualTo(LocalDateTime.parse("2021-03-02T12:44:55"))
        }

        @Test
        fun `get empty date when no entries`() {
            val actual = repo.getNewestDate()

            assertThat(actual).isNull()
        }
    }
}