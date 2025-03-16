package nat20.kamppisserver.configuration


import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(private val secretKey: String): OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // ✅ Only apply JWT filtering to /api/login/protected
        println("Checking if request should be filtered: ${request.requestURI}")
        return !request.requestURI.startsWith("/api/login/protected")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authorizationHeader = request.getHeader("Authorization")
            println("Checking Authorization header: $authorizationHeader")

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                val token = authorizationHeader.removePrefix("Bearer ").trim()
                val email = validateTokenAndGetEmail(token)

                if (email != null) {
                    println("Valid token found for email: $email")
                    val authentication = UsernamePasswordAuthenticationToken(email, null, emptyList())
                    SecurityContextHolder.getContext().authentication = authentication
                    request.setAttribute("email", email)
                }
            }
        } catch (ex: ExpiredJwtException) {
            println("Token expired: ${ex.message}")
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token has expired")
            return
        } catch (ex: JwtException) { // Other JWT exceptions (invalid token, malformed token, etc.)
            println("Invalid token: ${ex.message}")
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token")
            return
        } catch (ex: Exception) {
            println("Unexpected error in JWT filter: ${ex.message}")
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error")
            return
        }
        filterChain.doFilter(request, response)
    }

private fun validateTokenAndGetEmail(token: String): String? {
            val key = Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8))
            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload // claims
            val email = claims.subject
            println("Extracted email from JWT: $email")
            return email
    }
}