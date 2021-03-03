package xyz.lischka.textanalysis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import xyz.lischka.textanalysis.Processing.countWordsInBlogPost
import xyz.lischka.textanalysis.events.NewBlogPostPublished
import xyz.lischka.textanalysis.events.WordCountAnalysisResult
import xyz.lischka.textanalysis.model.WordCount

@Component
class NewBlogPostEventListener {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, WordCountAnalysisResult>

    @KafkaListener(topics = ["blogposts-json"])
    fun processMessage(newBlogPostPublishedEvent: NewBlogPostPublished) {
        val wordCount: WordCount = countWordsInBlogPost(newBlogPostPublishedEvent)

        kafkaTemplate.send(
            "analysis-json",
            WordCountAnalysisResult("someId", "some HTML content", wordCount.count)
        )
    }
}
