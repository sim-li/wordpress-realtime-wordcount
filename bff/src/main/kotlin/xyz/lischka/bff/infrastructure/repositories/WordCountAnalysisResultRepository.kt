package xyz.lischka.bff.infrastructure.repositories

import org.springframework.stereotype.Repository
import xyz.lischka.bff.entities.events.WordCountAnalysisResult

@Repository
class WordCountAnalysisResultRepository {
    val db: MutableList<WordCountAnalysisResult> = mutableListOf()

    fun clear() {
        db.clear()
    }

    fun save(wordCountAnalysisResult: WordCountAnalysisResult) {
        db += wordCountAnalysisResult
    }

    fun save(wordCountAnalysisResults: List<WordCountAnalysisResult>) {
        db += wordCountAnalysisResults
    }

    fun getAll(): List<WordCountAnalysisResult> {
        return db
    }
}
