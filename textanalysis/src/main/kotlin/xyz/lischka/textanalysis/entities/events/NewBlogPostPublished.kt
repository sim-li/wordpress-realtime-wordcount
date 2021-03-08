package xyz.lischka.textanalysis.entities.events

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class NewBlogPostPublished (
    @JsonProperty("id")  var id: String,
    @JsonProperty("htmlContent") var htmlContent: String,
    @JsonProperty("date") val date: LocalDateTime
)
