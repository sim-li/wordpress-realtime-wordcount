package xyz.lischka.bff.infrastructure.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import xyz.lischka.bff.entities.events.WordCountAnalysisResult
import xyz.lischka.bff.infrastructure.repositories.WordCountAnalysisResultRepository

@RestController
class WordCountAnalysisResultController {
    @Autowired
    private lateinit var repo: WordCountAnalysisResultRepository

    @GetMapping("/wordcount")
    //TODO: Write Test
    fun wordCountAnalysisResult(): List<WordCountAnalysisResult> {
        return repo.getAll()
    }
}
