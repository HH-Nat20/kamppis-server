package nat20.kamppisserver.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import nat20.kamppisserver.security.JwtHandshakeInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.messaging.converter.DefaultContentTypeResolver
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.util.MimeTypeUtils
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

/**
 * WebSocket config class. Specifies destinations from which to send and receive
 * chat messages. Also includes a message converter.
 */
@Configuration
@Order(HIGHEST_PRECEDENCE + 50)
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val jwtHandshakeInterceptor: JwtHandshakeInterceptor,
) : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // Clients subscribe to messages at ("/user/matches/{matchId}/messages")
        registry.enableSimpleBroker("/user", "/topic", "/app")

        // Clients send messages to ("/app/matches/{matchId}/messages")
        registry.setApplicationDestinationPrefixes("/app", "/topic", "/user")

        registry.setUserDestinationPrefix("/user")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws") // WebSocket entry point
            .setAllowedOrigins("*")
            .addInterceptors(jwtHandshakeInterceptor)
    }

}
