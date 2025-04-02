package nat20.kamppisserver.service

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

    suspend fun exchangeCodeForToken(code: String): String? {
        return try {
            val response = webClient.post()
                .uri("/login/oauth/access_token?client_id=$clientId&client_secret=$clientSecret&code=$code")
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(GitHubTokenResponse::class.java)
                //.block() // Blocking for simplicity; use reactive programming in production.
                .awaitSingle() // From coroutines reactor

            println("Used client-id: $clientId")
            println("GitHub Auth response: $response")
            response.access_token
        } catch (e: WebClientResponseException) { // Handles HTTP errors properly
            println("GitHub Auth error: ${e.statusCode} - ${e.responseBodyAsString}")
            null
        } catch (e: Exception) {
            println("Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun getGitHubEmail(accessToken: String): String? {
        return try {
            val response = webClient.get()
                .uri("https://api.github.com/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
                .retrieve()
                .bodyToMono(GitHubUserResponse::class.java)
                .awaitSingle()

            response.email
        } catch (e: WebClientResponseException) { // Handles HTTP errors properly
            println("Error fetching GitHub User email: ${e.statusCode} - ${e.responseBodyAsString}")
            null
        } catch (e: Exception) {
            println("Unexpected error: ${e.message}")
            null
        }
    }

}

data class GitHubTokenResponse(val access_token: String?, val token_type: String?, val scope: String?)
data class GitHubUserResponse(val email: String?)
