package xyz.lischka.scraping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import xyz.lischka.scraping.domain.wpblogpost.BlogPost
import xyz.lischka.textanalysis.events.NewBlogPostPublished

@Service
class WordpressService {
    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, NewBlogPostPublished>

    fun getAllBlogPosts() {
        val response = restTemplate.getForObject(
            Constants.LOCAL_WP_DEMOSERVER_URL, Array<BlogPost>::class.java
        ) // todo: Request may return empty, handle this.

        System.out.println("{D} Response: " + response?.joinToString())
    }

    fun sendCoolMessage() {
        kafkaTemplate.send("blogposts-json", NewBlogPostPublished("some_cool_id", "hey, this is a blogpost <b></b>"))
        System.out.println("Sent a cool message")
    }
}
