package xyz.lischka.scraping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import org.springframework.boot.CommandLineRunner

import org.springframework.context.annotation.Bean
import xyz.lischka.scraping.infrastructure.config.Constants.Companion.REFRESH_INTERVAL
import xyz.lischka.scraping.infrastructure.rest.WordPressRestClient
import xyz.lischka.scraping.services.BlogPostScrapingAndSendingService
import java.lang.Exception
import java.time.LocalDateTime


@Component
class WordpressRunner {
    @Autowired
    private lateinit var service: BlogPostScrapingAndSendingService

    @Scheduled(fixedRate = REFRESH_INTERVAL)
    fun fetchEntries() {
        service.scrapeAndSendNewBlogPosts()
    }
}
