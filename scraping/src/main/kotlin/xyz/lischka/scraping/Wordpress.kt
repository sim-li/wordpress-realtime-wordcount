package xyz.lischka.scraping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

import org.springframework.boot.CommandLineRunner

import org.springframework.boot.web.client.RestTemplateBuilder

import org.springframework.context.annotation.Bean
import java.lang.Exception


@Component
class Wordpress {
    @Autowired
    private lateinit var wordpressService: WordpressService

    @Scheduled(fixedRate = 5000)
    fun fetchEntries() {
        //wordpressService.getAllBlogPosts()
    }

    @Bean
    @Throws(Exception::class)
    fun run(wordpressService: WordpressService): CommandLineRunner? {
        return CommandLineRunner {
            System.out.println("Running the runner on app start, just for fun")
            System.out.println("Runner hitting it.")
            wordpressService.sendCoolMessage()
            //wordpressService.getAllBlogPosts()
        }
    }
}
