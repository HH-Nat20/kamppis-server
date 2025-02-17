package nat20.kamppisserver.configuration

import nat20.kamppisserver.StartupRunner
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

/**
 * Component for reacting to changes in chat sessions. Only gives logger feedback
 * on disconnections atm.
 */
@Component
class WebSocketEventListener {

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val logger = LoggerFactory.getLogger(StartupRunner::class.java)

        val headerAccessor: StompHeaderAccessor = StompHeaderAccessor.wrap(event.message)
        val username: String? = headerAccessor.sessionAttributes?.get("username")?.toString()

        if (username != null) {
            logger.info("User disconnected: $username")
        }

    }
}