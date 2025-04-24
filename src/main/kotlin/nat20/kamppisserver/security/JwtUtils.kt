package nat20.kamppisserver.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.*

// This is an "object" which is a singleton in Kotlin
object JwtUtils {

    @Value("\${JWT_SECRET}")
    private lateinit var KEY: String

    private val secretKey = Keys.hmacShaKeyFor(KEY.toByteArray())

    // Method to generate a valid JWT token
    fun generateJwtToken(email: String): String {
        return Jwts.builder()
            .claims()
            .subject(email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 3600000 * 72)) // Expires in 72h
            .and()
            .signWith(secretKey)
            .compact()
    }

    // Method to generate an expired JWT token
    fun generateExpiredToken(email: String): String {
        val expiredDate = Date(System.currentTimeMillis() - 1000 * 60) // Expired 1 minute ago
        return Jwts.builder()
            .claims()
            .subject(email)
            .issuedAt(Date())
            .expiration(expiredDate) // Expired
            .and()
            .signWith(secretKey)
            .compact()
    }

    // Method to validate token and extract email from it
    fun validateTokenAndGetEmail(token: String): String? {
        val email = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload // claims
            .subject // email
        println("Extracted email from JWT: $email")
        return email
    }
}