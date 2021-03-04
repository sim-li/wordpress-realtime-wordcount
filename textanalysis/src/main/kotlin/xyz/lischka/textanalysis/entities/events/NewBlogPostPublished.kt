package xyz.lischka.textanalysis.entities.events

import com.fasterxml.jackson.annotation.JsonProperty

data class NewBlogPostPublished (
    @JsonProperty("id")  var id: String,
    @JsonProperty("htmlContent") var htmlContent: String
)
