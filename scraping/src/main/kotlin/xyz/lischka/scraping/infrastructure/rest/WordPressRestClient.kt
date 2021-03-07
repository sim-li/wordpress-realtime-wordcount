package xyz.lischka.scraping.infrastructure.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import xyz.lischka.scraping.entities.rest.BlogPost
import xyz.lischka.scraping.infrastructure.config.Constants
import xyz.lischka.scraping.services.BlogPostScrapingAndSendingService
import java.time.LocalDateTime

@Service
//TODO: Write tests
class WordPressRestClient {
    var logger: Logger = LoggerFactory.getLogger(WordPressRestClient::class.java)

    @Autowired
    private lateinit var restTemplate: RestTemplate

    fun getAllBlogPosts(): List<BlogPost>? {
        try {
            return restTemplate.getForObject(
                Constants.WP_SERVER_URL, Array<BlogPost>::class.java
            )?.asList()
        } catch (ex: RestClientException) {
            logger.error("Error getting all blog posts via rest", ex)
            return null
        }
    }

    fun getBlogPostsAfter(afterDate: LocalDateTime?): List<BlogPost>? {
        val builder = UriComponentsBuilder.fromHttpUrl(Constants.WP_SERVER_URL)
            .queryParam("after",  afterDate.toString())
        try {
            return restTemplate.getForEntity(
                builder.toUriString(),
                Array<BlogPost>::class.java,
            )?.body?.toList()
        } catch (ex: RestClientException) {
            logger.error("Error getting blog posts after ${afterDate} via rest", ex)
            return null
        }
    }
}
