package xyz.lischka.textanalysis.events

import com.fasterxml.jackson.annotation.JsonProperty

data class NewBlogPostPublished (
    @JsonProperty("id")  var id: String,
    @JsonProperty("htmlContent") var htmlContent: String
)
