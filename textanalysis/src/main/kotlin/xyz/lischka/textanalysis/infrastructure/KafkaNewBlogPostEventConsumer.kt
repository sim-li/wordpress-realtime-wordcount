package xyz.lischka.textanalysis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import xyz.lischka.textanalysis.entities.events.NewBlogPostPublished
import xyz.lischka.textanalysis.entities.events.WordCountAnalysisResult
import xyz.lischka.textanalysis.services.BlogEntryProcessingService

@Component
class KafkaNewBlogPostEventConsumer {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, WordCountAnalysisResult>

    @Autowired
    private lateinit var service: BlogEntryProcessingService

    @KafkaListener(topics = ["blogposts-json"])
    fun processMessage(newBlogPostPublishedEvent: NewBlogPostPublished) {
        kafkaTemplate.send(
            "analysis-json",
            service.process(newBlogPostPublishedEvent)
        )
    }
}
