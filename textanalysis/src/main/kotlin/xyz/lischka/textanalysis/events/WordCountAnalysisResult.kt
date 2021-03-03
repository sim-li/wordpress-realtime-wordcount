package xyz.lischka.textanalysis.events

import com.fasterxml.jackson.annotation.JsonProperty

data class WordCountAnalysisResult (
    @JsonProperty("id")  var id: String,
    @JsonProperty("htmlContent") var htmlContent: String,
    @JsonProperty("counts")  var counts: Map<String, Int>
)
