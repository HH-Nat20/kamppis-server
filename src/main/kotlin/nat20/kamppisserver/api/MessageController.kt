package nat20.kamppisserver.api

import nat20.kamppisserver.domain.Message
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.messaging.simp.SimpMessageHeaderAccessor

/**
 * Controller for Message.
 *
 * Lacking PUT and DELETE methods and quite a lot of logic.
 */
@RestController
class MessageController {

    @MessageMapping("/matches/{matchId}/messages") // Listen to messages from /app/matches/{matchId}/messages
    @SendTo("/topic/matches/{matchId}/messages") // Send content to subscribers of /topic/matches/{matchId}/messages
    fun sendMessage(@DestinationVariable matchId: String, @Payload message: Message): Message {
        println("Received message for match $matchId: ${message.content}")
        return message // This will be broadcast to subscribed clients
    }

    /** Still experimenting, might never be used
    @GetMapping("/matches/{matchId}/messages")
    fun chat(@PathVariable matchId: String, model: Model): String {
        model.addAttribute("matchId", matchId)
        return "chat"
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    fun addUser(@Payload message: Message, headerAccessor: SimpMessageHeaderAccessor): Message {
        // Add username in websocket session
        headerAccessor.sessionAttributes?.put("username", message.sender)
        return message
    }
    */

}