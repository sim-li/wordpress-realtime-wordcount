package xyz.lischka.scraping.infrastructure.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import xyz.lischka.scraping.services.BlogPostScrapingAndSendingService

@RestController
class ReplayRestController {
    var logger: Logger = LoggerFactory.getLogger(ReplayRestController::class.java)


    @Autowired
        private lateinit var service: BlogPostScrapingAndSendingService

        @GetMapping("/replay")
        @CrossOrigin
        //TODO: Write Test
        fun replay() {
            logger.info("Starting replay...")

            service.reset()
            service.scrapeAndSendNewBlogPosts()

            logger.info("Finished replay.")
    }
}
