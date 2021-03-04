package xyz.lischka.textanalysis.entities.events

import com.fasterxml.jackson.annotation.JsonProperty

data class WordCountAnalysisResult (
    @JsonProperty("id")  var id: String,
    @JsonProperty("htmlContent") var htmlContent: String,
    @JsonProperty("counts")  var counts: Map<String, Int>
)
