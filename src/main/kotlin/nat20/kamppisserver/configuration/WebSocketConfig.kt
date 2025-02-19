package nat20.kamppisserver.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
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
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

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
            //.withSockJS()
    }

//    override fun configureMessageConverters(messageConverters: MutableList<MessageConverter>): Boolean {
//        val resolver = DefaultContentTypeResolver()
//        resolver.defaultMimeType = MimeTypeUtils.APPLICATION_JSON
//        val converter = MappingJackson2MessageConverter()
//        converter.objectMapper = ObjectMapper()
//        converter.contentTypeResolver = resolver
//        messageConverters.add(converter)
//
//        return false
//    }

}
