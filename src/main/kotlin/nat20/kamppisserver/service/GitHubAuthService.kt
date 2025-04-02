package nat20.kamppisserver.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

@Service
class GitHubAuthService {
    @Value("\${GITHUB_CLIENT_ID:dummy-client-id}")
    final lateinit var clientId: String

    @Value("\${GITHUB_CLIENT_SECRET:dummy-client-secret}")
    final lateinit var clientSecret: String

    private val webClient = WebClient.builder()
        .baseUrl("https://github.com")
        .build()

    private val objectMapper = ObjectMapper().registerKotlinModule()

    suspend fun exchangeCodeForToken(code: String): String? {
        return try {
            val responseString = webClient.post()
                .uri("/login/oauth/access_token?client_id=$clientId&client_secret=$clientSecret&code=$code")
                .header(HttpHeaders.ACCEPT, "application/json")
                .retrieve()
                .bodyToMono(String::class.java) // Get raw JSON as a String first
                .awaitSingle()

            println("Used client-id: $clientId")
            println("Raw GitHub Auth response: $responseString")

            // Now, manually parse it into our expected object
            val response = objectMapper.readValue(responseString, GitHubTokenResponse::class.java)

            println("Parsed GitHub Token response: $response")
            response.access_token
        } catch (e: WebClientResponseException) { // Handles HTTP errors properly
            println("GitHub Auth API error: ${e.statusCode} - ${e.responseBodyAsString}")
            null
        } catch (e: Exception) {
            println("Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun getGitHubUserInfo(accessToken: String): GitHubUserResponse? {
        return try {
            val responseString = webClient.get()
                .uri("https://api.github.com/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
                .header(HttpHeaders.ACCEPT, "application/json") // Ensures JSON response
                .retrieve()
                .bodyToMono(String::class.java) // Get raw JSON as a String first
                .awaitSingle()

            println("Raw GitHub User response: $responseString")

            // Now, manually parse it into our expected object
            val response = objectMapper.readValue(responseString, GitHubUserResponse::class.java)

            println("Parsed GitHub User response: $response")
            response
        } catch (e: WebClientResponseException) { // Handles HTTP errors properly
            println("GitHub User API Error: ${e.statusCode} - ${e.responseBodyAsString}")
            null
        } catch (e: Exception) {
            println("Unexpected error: ${e.message}")
            null
        }
    }

}
@JsonIgnoreProperties(ignoreUnknown = true)
data class GitHubTokenResponse(val access_token: String?, val token_type: String?, val scope: String?)
@JsonIgnoreProperties(ignoreUnknown = true)
data class GitHubUserResponse(val id: Long, val login: String, val email: String?, )
