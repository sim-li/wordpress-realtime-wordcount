package xyz.lischka.scraping.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import xyz.lischka.scraping.entities.rest.BlogPost
import xyz.lischka.scraping.infrastructure.repositories.BlogPostRepository
import xyz.lischka.scraping.infrastructure.rest.WordPressRestClient
import xyz.lischka.textanalysis.events.NewBlogPostPublished
import java.time.LocalDateTime

@Service
class WordpressScrapingService {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, NewBlogPostPublished>

    @Autowired
    private lateinit var wordPressRestClient: WordPressRestClient

    @Autowired
    private lateinit var blogPostRepository: BlogPostRepository

    private var latestTimeStamp: LocalDateTime? = null

    fun checkIfNewBlogPosts() {
        //move this code to repo
        var newBlogPosts: List<BlogPost>?  = null
        if (blogPostRepository.isEmpty()) {
            newBlogPosts = wordPressRestClient.getAllBlogPosts()
        } else {
            val date = blogPostRepository.getNewestDate()
            if (date != null) {
                newBlogPosts = wordPressRestClient.getBlogPostsAfterDate(
                    date
                )
                newBlogPosts?.let { b ->
                    blogPostRepository.save(b)
                }
            }
        }
        newBlogPosts?.forEach { b ->
            kafkaTemplate.send(
                "blogposts-json",
                NewBlogPostPublished(b.id, b.content?.rendered ?: "")
            )
        }
    }
}
