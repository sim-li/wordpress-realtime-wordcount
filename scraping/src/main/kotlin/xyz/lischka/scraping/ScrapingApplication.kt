package xyz.lischka.scraping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import java.lang.Exception
import java.util.*

@SpringBootApplication
@EnableScheduling
class ScrapingApplication

	fun main(args: Array<String>) {
		runApplication<ScrapingApplication>(*args)
	}



