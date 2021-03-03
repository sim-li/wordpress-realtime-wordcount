package xyz.lischka.scraping.domain.wpblogpost

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BlogPost (
    val id: String = ""
)