package nat20.kamppisserver.api

import nat20.kamppisserver.domain.Message
import nat20.kamppisserver.service.UserService
//import nat20.kamppisserver.service.MessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*

/**
 * Controller for Message.
 *
 * Lacking PUT and DELETE methods and quite a lot of logic.
 */
@RestController
class MessageController(
//    private val messageService: MessageService,
    private val userService: UserService,
    private val messagingTemplate: SimpMessagingTemplate
) {

//    @PostMapping("/matches/{matchId}/start-chat")
//    fun startChat(@PathVariable matchId: Long): ResponseEntity<Message> {
//        val message = messageService.startChat(matchId)
//        return ResponseEntity.ok(message)
//    }

    @MessageMapping("/matches/{matchId}/messages") // Listen to messages from /app/matches/{matchId}/messages
    //@SendTo("/user/matches/{matchId}/messages") // Send content to subscribers of /user/matches/{matchId}/messages
    // SendTo requires /topic by default, but we are using /user so we need to manually set the destination
    fun sendMessage(
        @DestinationVariable matchId: String,
        @Payload message: Message,
        headerAccessor: SimpMessageHeaderAccessor
    ): Message
        {
        val email = headerAccessor.getFirstNativeHeader("user-email")
            ?: throw IllegalAccessException("Unauthorized: Missing user-email header")

        val user = userService.findUserByEmail(email)
            ?: throw IllegalAccessException("Unauthorized: User does not exist")

        println("User ${user.email} sent message to match $matchId: ${message.content}")

        // Ensure we send to the correct broker destination
        val destination = "/user/matches/$matchId/messages"

        messagingTemplate.convertAndSend(destination, message)

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