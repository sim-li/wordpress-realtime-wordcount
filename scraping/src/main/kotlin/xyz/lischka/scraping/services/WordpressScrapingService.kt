package xyz.lischka.scraping.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import xyz.lischka.scraping.infrastructure.rest.WordPressRestClient
import xyz.lischka.textanalysis.events.NewBlogPostPublished

@Service
class WordpressScrapingService {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, NewBlogPostPublished>

    @Autowired
    private lateinit var wordPressRestClient: WordPressRestClient

    fun scrapeNewBlogPosts() {

    }

    fun publishNewBlogPosts() {
        kafkaTemplate.send("blogposts-json", NewBlogPostPublished("some_cool_id", "hey, this is a blogpost <b></b>"))
    }
}
