package nat20.kamppisserver.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class GitHubAuthService {
    @Value("\${github.client-id}")
    final lateinit var clientId: String

    @Value("\${github.client-secret}")
    final lateinit var clientSecret: String

    private val webClient = WebClient.builder()
        .baseUrl("https://github.com")
        .build()

    fun exchangeCodeForToken(code: String): String? {
        val response = webClient.post()
            .uri("/login/oauth/access_token?client_id=$clientId&client_secret=$clientSecret&code=$code")
            .header("Accept", "application/json")
            .retrieve()
            .bodyToMono(Map::class.java)
            .block() // Blocking for simplicity; use reactive programming in production.

        return response?.get("access_token") as? String
    }

    fun getGitHubEmail(accessToken: String): String? {
        val response = webClient.get()
            .uri("https://api.github.com/user")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .bodyToMono(Map::class.java)
            .block()

        return response?.get("email").toString()
    }
}