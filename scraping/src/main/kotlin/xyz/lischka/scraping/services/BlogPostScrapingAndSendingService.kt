package xyz.lischka.scraping.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import xyz.lischka.scraping.entities.rest.BlogPost
import xyz.lischka.scraping.infrastructure.config.Constants.Companion.BLOGPOST_KAFKA_TOPIC
import xyz.lischka.scraping.infrastructure.rest.WordPressRestClient
import xyz.lischka.textanalysis.events.NewBlogPostPublished
import java.time.LocalDateTime

@Service
// TODO: Yup, that's right. This one has two responsibilities. We might split them
// up later to have one Service for Scraping periodically and the other one
// for sending. But for now, let's make it explicit.
// We might also go on and extract certain aspects of this into other objects:
class BlogPostScrapingAndSendingService {
    var logger: Logger = LoggerFactory.getLogger(BlogPostScrapingAndSendingService::class.java)

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, NewBlogPostPublished>

    @Autowired
    private lateinit var rest: WordPressRestClient

    private var dateOfLastBlogPostSent: LocalDateTime? = null

    fun scrapeAndSendNewBlogPosts() {
        var blogPostsToSend = getUnsentBlogPosts()
        blogPostsToSend?.forEach { bp ->
           if (isFirstBlogPostSent() || isNewBlogPost(bp)) {
               sendViaKafka(bp)
               dateOfLastBlogPostSent = bp.date
               logger.info("Sent out new blogpost, with date ${bp.date}")
            }
        }
    }

    private fun sendViaKafka(bp: BlogPost) {
        kafkaTemplate.send(
            BLOGPOST_KAFKA_TOPIC,
            NewBlogPostPublished(
                id = bp.id,
                htmlContent = bp.content?.rendered ?: "",
                date = bp.date ?: LocalDateTime.now()
            )
        )
    }

    private fun getUnsentBlogPosts() = if (isFirstBlogPostSent()) {
        //TODO: Test sortedBy call
        rest.getAllBlogPosts()?.sortedBy{ post -> post.date }
    } else {
        //TODO: Test sortedBy call
        rest.getBlogPostsAfter(dateOfLastBlogPostSent)?.sortedBy{ post -> post.date }
    }

    private fun isFirstBlogPostSent() = dateOfLastBlogPostSent == null

    private fun isNewBlogPost(bp: BlogPost) = bp.date?.isAfter(dateOfLastBlogPostSent) == true
}
