package xyz.lischka.scraping

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.runner.RunWith
import org.mockito.Mock
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.web.client.RestTemplate
import org.mockito.InjectMocks
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import junit.runner.Version.id
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.junit.Before
import org.junit.Test
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import java.io.File
import java.net.URI
import java.net.URL

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [SpringTestConfig::class])
class WordPressRestClientTest {
    @Mock
    private lateinit var restTemplate: RestTemplate

    @InjectMocks
    private val wordPressService: WordpressService = WordpressService()

    private lateinit var mockServer: MockRestServiceServer

    private val mapper: ObjectMapper = ObjectMapper()


    @Before
    fun init() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @Test
    fun `When then return `() {
       // val emp = Employee("E001", "Eric Simmons")
       val resource: URL = javaClass.classLoader.getResource("mockResponse.json")
       val text = File(resource.toURI()).readLines();

        mockServer.expect(
            ExpectedCount.once(),
            requestTo(URI("http://www.google.de"))
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(
                        "{}"
                    )
            )

        val listOfBlogPosts = wordPressService.getAllBlogPosts()
        mockServer.verify()
        //Assert.assertEquals(null, listOfBlogPosts)

        assertThat(listOfBlogPosts).isEqualTo("kuku")
    }
}