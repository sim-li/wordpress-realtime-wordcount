package xyz.lischka.scraping.infrastructure.config

class ServerUrlConfig {
    companion object {
        const val LOCAL_WP_DEMOSERVER_URL = "http://localhost:9001/posts"
        const val REAL_WP_SERVER = "https://www.internate.org/wp-json/wp/v2/posts"
    }
}
