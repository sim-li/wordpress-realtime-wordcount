package xyz.lischka.scraping.entities.rest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class BlogPost (
    val id: String = "",
    val date: LocalDateTime? = null,
    val content: BlogPostContent? = null
)