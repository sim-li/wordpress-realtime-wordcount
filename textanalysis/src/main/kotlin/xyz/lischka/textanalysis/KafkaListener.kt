package xyz.lischka.textanalysis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import xyz.lischka.textanalysis.processing.WordCount
import xyz.lischka.textanalysis.events.NewBlogPostPublished
import xyz.lischka.textanalysis.events.WordCountAnalysisResult

@Component
class NewBlogPostEventListener {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, WordCountAnalysisResult>

    @KafkaListener(topics = ["blogposts-json"])
    fun processMessage(ev: NewBlogPostPublished) {
        val s: List<String> = ev.htmlContent
            .split(" ")

        val count: Map<String, Int> = WordCount.count(s)

        kafkaTemplate.send("analysis-json",  WordCountAnalysisResult("someId", "some HTML content", count))
    }
}
