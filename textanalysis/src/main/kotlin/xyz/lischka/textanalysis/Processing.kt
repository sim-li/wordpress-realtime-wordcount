package xyz.lischka.textanalysis

import org.jsoup.Jsoup
import xyz.lischka.textanalysis.events.NewBlogPostPublished
import xyz.lischka.textanalysis.model.WordCount
import xyz.lischka.textanalysis.model.Words

object Processing {
    fun countWordsInBlogPost(blogPostPublishedEvent: NewBlogPostPublished): WordCount {
        // TODO: Strip special characters like parenthesis () {}
        val stripped: String = stripHtml(blogPostPublishedEvent.htmlContent)
        val words: Words = splitTextToWords(stripped)
        return countWords(words)
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
