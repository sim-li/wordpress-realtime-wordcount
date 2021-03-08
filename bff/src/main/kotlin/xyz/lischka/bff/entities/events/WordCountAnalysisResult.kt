package xyz.lischka.bff.entities.events

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class WordCountAnalysisResult (
    @JsonProperty("id")  var id: String,
    @JsonProperty("htmlContent") var htmlContent: String,
    @JsonProperty("counts")  var counts: Map<String, Int>,
    @JsonProperty("date") val date: LocalDateTime
)
