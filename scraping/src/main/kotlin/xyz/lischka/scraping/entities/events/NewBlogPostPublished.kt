package xyz.lischka.textanalysis.events

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class NewBlogPostPublished (
    @JsonProperty("id")  val id: String,
    @JsonProperty("htmlContent") val htmlContent: String,
    @JsonProperty("date") val date: LocalDateTime
)
