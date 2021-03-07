package xyz.lischka.bff

import org.springframework.stereotype.Repository

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
