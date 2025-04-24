package nat20.kamppisserver.security

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception

@Component
class JwtHandshakeInterceptor(private val jwtUtils: JwtUtils) : HandshakeInterceptor {

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        if (request is ServletServerHttpRequest && response is ServletServerHttpResponse) {
            val servletRequest = request.servletRequest
            val servletResponse = response.servletResponse
            val token = servletRequest.getHeader("Authorization")?.removePrefix("Bearer ")?.trim()
                ?: servletRequest.getParameter("token")
            val email: String?
            try {
                email = token?.let { jwtUtils.validateTokenAndGetEmail(it) }
            } catch (ex: Exception){
                println("Invalid token: ${ex.message}")
                servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token")
                return false
            }

            if (email != null) {
                attributes["email"] = email
                println("Websocket authenticated for email: $email")
                val auth = UsernamePasswordAuthenticationToken(email, null, emptyList())
                SecurityContextHolder.getContext().authentication = auth // Set user auth context to be used in controllers as principal
                return true
            }
        }
        return false // reject connection if no valid token
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
        // Nothing to do after
    }
}