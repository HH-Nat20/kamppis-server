package nat20.kamppisserver.security


import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // Disable filter in order to:
        // Allow GET requests to /api/db-health and /api/health
        // Allow POST requests to /api/login/**

        val uri = request.requestURI
        val method = request.method

        return when {
            method == "GET" && (uri == "/api/db-health" || uri == "/api/health") -> true
            method == "POST" && uri.startsWith("/api/login") -> true
            else -> false
        }
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
                val email = JwtUtils.validateTokenAndGetEmail(token)

                if (email != null) {
                    println("Valid token found for email: $email")

                    // Authenticate user
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
        // Ensure filterChain.doFilter is always called
        filterChain.doFilter(request, response)
    }


}