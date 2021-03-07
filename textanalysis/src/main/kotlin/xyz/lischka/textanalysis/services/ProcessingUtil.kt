package xyz.lischka.textanalysis.services

import org.jsoup.Jsoup
import xyz.lischka.textanalysis.entities.events.NewBlogPostPublished
import xyz.lischka.textanalysis.entities.WordCount
import xyz.lischka.textanalysis.entities.Words
import xyz.lischka.textanalysis.entities.events.WordCountAnalysisResult

object ProcessingUtil {
    fun countWordsInBlogPost(blogPostPublishedEvent: NewBlogPostPublished): WordCountAnalysisResult {
        // TODO: Strip special characters like parenthesis () {}
        val stripped: String = stripHtml(blogPostPublishedEvent.htmlContent)
        val words: Words = splitTextToWords(stripped)

        val (id, html) = blogPostPublishedEvent
        val wordCount = countWords(words)
        return WordCountAnalysisResult(id, html, wordCount.count)
    }

    fun stripHtml(html: String): String {
        return Jsoup.parse(html).text();
    }

    fun splitTextToWords(text: String): Words {
        // TODO: Move to Words class
        return if (text.isEmpty()) Words(emptyList()) else Words(text.split(" "))
    }

    fun countWords(w: Words): WordCount {
        val occurrences: MutableMap<String, Int> = HashMap()
        w.words.forEach { w ->
            occurrences[w] = if (occurrences.containsKey(w)) occurrences[w]!! + 1 else 1
        }
        return WordCount(occurrences)
    }
}
