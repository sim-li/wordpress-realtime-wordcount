package xyz.lischka.scraping.infrastructure.config

import org.springframework.web.client.RestTemplate
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan("xyz.lischka")
class SpringTestConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
