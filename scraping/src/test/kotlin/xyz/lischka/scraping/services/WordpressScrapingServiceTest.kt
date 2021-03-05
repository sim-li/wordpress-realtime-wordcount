package xyz.lischka.scraping.services

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.junit4.SpringRunner
import xyz.lischka.scraping.infrastructure.rest.WordPressRestClient
import xyz.lischka.textanalysis.events.NewBlogPostPublished
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import xyz.lischka.scraping.entities.rest.BlogPost
import xyz.lischka.scraping.entities.rest.BlogPostContent
import xyz.lischka.scraping.infrastructure.repositories.BlogPostRepository
import java.time.LocalDateTime


@RunWith(SpringRunner::class)
@SpringBootTest
class WordpressScrapingServiceTest {
    @Mock
    private lateinit var client: WordPressRestClient

    @Mock
    private lateinit var kafkaTemplate: KafkaTemplate<String, NewBlogPostPublished>

    @Mock
    private lateinit var blogPostRepository: BlogPostRepository

    @InjectMocks
    private lateinit var service: WordpressScrapingService

    @Nested
    inner class kafka_message() {
        @Test
        fun `should be sent when inititally one blog post is retrieved`() {
            `when`(client.getAllBlogPosts()).thenReturn(
                listOf(
                    BlogPost(
                        id = "some-id",
                        date = LocalDateTime.parse("2021-02-02T18:44:55"),
                        content = BlogPostContent(rendered = "some content")
                    )
                )
            )

            service.checkIfNewBlogPosts()

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
            `when`(client.getAllBlogPosts()).thenReturn(
                listOf(
                    BlogPost(
                        id = "some-id",
                        date = LocalDateTime.parse("2021-02-02T18:44:55"),
                        content = BlogPostContent(rendered = "some content")
                    )
                )
            )

            service.checkIfNewBlogPosts()
            service.checkIfNewBlogPosts()

            verify(kafkaTemplate,  times(1)).send(
                "blogposts-json",
                NewBlogPostPublished(
                    id = "some-id",
                    htmlContent = "some content"
                )
            )
        }
    }
}
