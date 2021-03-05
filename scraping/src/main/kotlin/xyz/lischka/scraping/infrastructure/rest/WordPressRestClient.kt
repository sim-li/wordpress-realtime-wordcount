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

    private var latestTimeStamp: LocalDateTime? = null

//    fun getNewBlogPosts(): List<BlogPost>? {
//        if(latestTimeStamp == null) {
//            val blogPosts = getAllBlogPosts()
//            val sorted = blogPosts?.sortedByDescending { post -> post.date }
//            latestTimeStamp = sorted?.get(0)?.date
//            return blogPosts
//        } else {
//            getBlogPostsAfterDate(latestTimeStamp)
//        }
//    }

    fun getAllBlogPosts(): List<BlogPost>? {
        try {
            return restTemplate.getForObject(
                Constants.LOCAL_WP_DEMOSERVER_URL, Array<BlogPost>::class.java
            )?.asList()
        } catch (e: RestClientException) {
            return null
        }
    }

    fun getBlogPostsAfterDate(afterDate: LocalDateTime): List<BlogPost>? {
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
