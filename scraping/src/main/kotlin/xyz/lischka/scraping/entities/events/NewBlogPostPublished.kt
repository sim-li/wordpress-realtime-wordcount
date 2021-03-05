package xyz.lischka.textanalysis.events

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class NewBlogPostPublished (
    @JsonProperty("id")  var id: String,
    @JsonProperty("htmlContent") var htmlContent: String
)
