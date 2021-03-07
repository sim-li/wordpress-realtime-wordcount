package xyz.lischka.scraping.infrastructure.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import xyz.lischka.scraping.entities.rest.BlogPost
import xyz.lischka.scraping.infrastructure.config.Constants
import java.time.LocalDateTime

@Service
//TODO: Write tests
class WordPressRestClient {
    @Autowired
    private lateinit var restTemplate: RestTemplate

    fun getAllBlogPosts(): List<BlogPost>? {
        try {
            return restTemplate.getForObject(
                Constants.LOCAL_WP_DEMOSERVER_URL, Array<BlogPost>::class.java
            )?.asList()
        } catch (e: RestClientException) {
            return null
        }
    }

    fun getBlogPostsAfter(afterDate: LocalDateTime?): List<BlogPost>? {
        val builder = UriComponentsBuilder.fromHttpUrl(Constants.REAL_WP_SERVER)
            .queryParam("after",  afterDate.toString())

        try {
            return restTemplate.getForEntity(
                builder.toUriString(),
                Array<BlogPost>::class.java,
            )?.body?.toList()
        } catch (e: RestClientException) {
            return null
        }
    }
}
