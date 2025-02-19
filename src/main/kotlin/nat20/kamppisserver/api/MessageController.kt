package nat20.kamppisserver.api

import nat20.kamppisserver.domain.Message
import nat20.kamppisserver.domain.MessageDTO
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.MessageRepository
import nat20.kamppisserver.service.UserService
//import nat20.kamppisserver.service.MessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping
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
    private val messagingTemplate: SimpMessagingTemplate,
    private val messageRepository: MessageRepository,
    private val matchRepository: MatchRepository
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
        @DestinationVariable matchId: Long,
        @Payload messageDTO: MessageDTO,
    )
        {
        val user = userService.findUserByEmail(messageDTO.senderEmail)
            ?: throw IllegalAccessException("Unauthorized: User does not exist")

        val match = matchRepository.findById(matchId)
            .orElseThrow { IllegalArgumentException("Match not found") }

        val message = Message(
            sender = user,
            match = match,
            content = messageDTO.content,
        )
        val savedMessage = messageRepository.save(message)
        // Ensure we send to the correct broker destination
        val destination = "/user/matches/$matchId/messages"
        println(destination)
        // Broadcast only the necessary details
        val responseDTO = MessageDTO(
            senderEmail = savedMessage.sender.email,
            matchId = savedMessage.match.id!!,
            content = savedMessage.content,
            createdAt = savedMessage.createdAt
        )
        // This will be broadcast to subscribed clients
        messagingTemplate.convertAndSend(destination, responseDTO)
    }

    @SubscribeMapping("/matches/{matchId}/messages")
    fun sendHistory(@DestinationVariable matchId: Long) {
        println("messageHistory requested")
        val messageHistory = messageRepository.findByMatchIdOrderByCreatedAtAsc(matchId)
        println("messageHistory retrieved")

// Convert to DTO and send previous messages to the user who just subscribed
        messageHistory.forEach { message ->
            val messageDTO = MessageDTO(
                senderEmail = message.sender.email,
                matchId = message.match.id!!,
                content = message.content,
                createdAt = message.createdAt
            )
            messagingTemplate.convertAndSend("/user/matches/$matchId/messages", messageDTO)
        }
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