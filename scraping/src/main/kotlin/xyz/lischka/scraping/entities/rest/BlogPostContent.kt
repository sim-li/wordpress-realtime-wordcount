package xyz.lischka.scraping.entities.rest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BlogPostContent (val rendered: String = "")