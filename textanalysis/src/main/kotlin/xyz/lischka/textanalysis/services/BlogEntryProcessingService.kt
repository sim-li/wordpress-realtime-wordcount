package xyz.lischka.textanalysis.services

import org.springframework.stereotype.Service
import xyz.lischka.textanalysis.entities.WordCount
import xyz.lischka.textanalysis.entities.events.NewBlogPostPublished
import xyz.lischka.textanalysis.entities.events.WordCountAnalysisResult

@Service
//TODO: Write test.
class BlogEntryProcessingService {
    fun process(newBlogPostPublished: NewBlogPostPublished): WordCountAnalysisResult {
        val wordCount: WordCount = ProcessingUtil.countWordsInBlogPost(newBlogPostPublished)
        val (id, html) = newBlogPostPublished

        return WordCountAnalysisResult(id, html, wordCount.count)
    }
}
