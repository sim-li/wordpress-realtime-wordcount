package xyz.lischka.scraping.services

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.junit4.SpringRunner
import xyz.lischka.scraping.infrastructure.rest.WordPressRestClient
import xyz.lischka.textanalysis.events.NewBlogPostPublished
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.annotation.DirtiesContext
import xyz.lischka.scraping.entities.rest.BlogPost
import xyz.lischka.scraping.entities.rest.BlogPostContent
import java.time.LocalDateTime


@RunWith(SpringRunner::class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WordpressScrapingServiceTest {
    @MockBean
    private lateinit var wordPressRestClient: WordPressRestClient

    @MockBean
    private lateinit var kafkaTemplate: KafkaTemplate<String, NewBlogPostPublished>

    @Autowired
    private lateinit var wordpressScrapingService: BlogPostScrapingAndSendingService

    @Nested
    inner class kafka_message() {
        @Test
        fun `should be sent when initially one blog post is retrieved`() {
            `when`(wordPressRestClient.getAllBlogPosts()).thenReturn(
                listOf(
                    BlogPost(
                        id = "some-id",
                        date = LocalDateTime.parse("2021-02-02T18:44:55"),
                        content = BlogPostContent(rendered = "some content")
                    )
                )
            )
            wordpressScrapingService.scrapeAndSendNewBlogPosts()

            verify(kafkaTemplate).send(
                "blogposts-json",
                NewBlogPostPublished(
                    id = "some-id",
                    htmlContent = "some content"
                )
            )
        }

        @Test
        fun `should not be sent again when no new blog post retrieved`() {
            `when`(wordPressRestClient.getAllBlogPosts()).thenReturn(
                listOf(
                    BlogPost(
                        id = "some-id",
                        date = LocalDateTime.parse("2021-02-02T18:44:55"),
                        content = BlogPostContent(rendered = "some content")
                    )
                )
            )

            wordpressScrapingService.scrapeAndSendNewBlogPosts()
            wordpressScrapingService.scrapeAndSendNewBlogPosts()

            verify(kafkaTemplate,  times(1)).send(
                "blogposts-json",
                NewBlogPostPublished(
                    id = "some-id",
                    htmlContent = "some content"
                )
            )
        }

        @Test
        fun `should be sent again when new blog post retrieved`() {
            `when`(wordPressRestClient.getAllBlogPosts()).thenReturn(
                listOf(
                    BlogPost(
                        id = "some-id",
                        date = LocalDateTime.parse("2021-02-02T18:44:55"),
                        content = BlogPostContent(rendered = "some content")
                    )
                )
            )
            `when`(wordPressRestClient.getBlogPostsAfter(any())).thenReturn(
                listOf(
                    BlogPost(
                        id = "another-id",
                        date = LocalDateTime.parse("2021-02-02T19:44:55"),
                        content = BlogPostContent(rendered = "some new content")
                    )
                )
            )

            wordpressScrapingService.scrapeAndSendNewBlogPosts()
            wordpressScrapingService.scrapeAndSendNewBlogPosts()

            verify(kafkaTemplate,  times(1)).send(
                "blogposts-json",
                NewBlogPostPublished(
                    id = "some-id",
                    htmlContent = "some content"
                )
            )
            verify(kafkaTemplate,  times(1)).send(
                "blogposts-json",
                NewBlogPostPublished(
                    id = "another-id",
                    htmlContent = "some new content"
                )
            )
        }

        @Test
        // TODO: actually a super edge case, could possible be removed from test and impl.
        fun `should not be sent again when blog post retrieved doesn't have a new date`() {
            `when`(wordPressRestClient.getAllBlogPosts()).thenReturn(
                listOf(
                    BlogPost(
                        id = "some-id",
                        date = LocalDateTime.parse("2021-02-02T18:44:55"),
                        content = BlogPostContent(rendered = "some content")
                    )
                )
            )
            `when`(wordPressRestClient.getBlogPostsAfter(any())).thenReturn(
                listOf(
                    BlogPost(
                        id = "another-id",
                        date = LocalDateTime.parse("2020-02-02T19:44:55"),
                        content = BlogPostContent(rendered = "some new old content")
                    )
                )
            )

            wordpressScrapingService.scrapeAndSendNewBlogPosts()
            wordpressScrapingService.scrapeAndSendNewBlogPosts()

            verify(kafkaTemplate,  times(1)).send(anyString(), any())
        }
    }
}
