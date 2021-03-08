package xyz.lischka.scraping.infrastructure.config

class Constants {
    companion object {
        const val LOCAL_WP_DEMOSERVER_URL = "http://localhost:9001/posts"
        const val REAL_WP_SERVER_URL = "https://www.internate.org/wp-json/wp/v2/posts"

        // const val WP_SERVER_URL = LOCAL_WP_DEMOSERVER_URL
        const val WP_SERVER_URL = REAL_WP_SERVER_URL

        const val BLOGPOST_KAFKA_TOPIC = "blogposts-json"

        const val REFRESH_INTERVAL = 5000L
    }
}
