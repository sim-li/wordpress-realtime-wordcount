package xyz.lischka.scraping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import org.springframework.boot.CommandLineRunner

import org.springframework.context.annotation.Bean
import org.springframework.web.util.UriComponentsBuilder
import xyz.lischka.scraping.infrastructure.rest.WordPressRestClient
import xyz.lischka.scraping.services.WordpressScrapingService
import java.lang.Exception
import java.time.LocalDateTime


@Component
class WordpressRunner {
    @Autowired
    private lateinit var service: WordpressScrapingService

    @Autowired
    private lateinit var client: WordPressRestClient

    @Scheduled(fixedRate = 5000)
    fun fetchEntries() {
//        service.getAllBlogPosts()
    }

    @Bean
    @Throws(Exception::class)
    fun run(wordpressService: WordpressScrapingService): CommandLineRunner? {
        return CommandLineRunner {
            val blogposts = client.getBlogPostsAfterDate(
                LocalDateTime.parse("2021-02-02T18:44:55")
            )
        }
    }
}
