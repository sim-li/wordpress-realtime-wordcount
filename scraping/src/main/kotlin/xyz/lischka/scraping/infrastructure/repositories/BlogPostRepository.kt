package xyz.lischka.scraping.infrastructure.repositories

import org.springframework.stereotype.Repository
import xyz.lischka.scraping.entities.rest.BlogPost
import java.time.LocalDateTime

@Repository
class BlogPostRepository {
    val db: MutableList<BlogPost> = mutableListOf()

    fun clear() {
        db.clear()
    }

    fun save(blogPost: BlogPost) {
        db += blogPost
    }

    fun save(blogPosts: List<BlogPost>) {
        db += blogPosts
    }

    fun getAll(): List<BlogPost> {
        return db
    }

    fun getNewestDate(): LocalDateTime? {
        if (db.isEmpty()) {
            return null
        }
        db.sortByDescending{ post -> post.date }
        return db[0].date
    }

    fun isEmpty(): Boolean {
        return db.isEmpty()
    }
}
