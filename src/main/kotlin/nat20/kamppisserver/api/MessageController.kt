package nat20.kamppisserver.api

import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.Message
import nat20.kamppisserver.domain.MessageDTO
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.MessageRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.UserService
//import nat20.kamppisserver.service.MessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.web.bind.annotation.*

/**
 * Controller for Message.
 */
@RestController
class MessageController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val messageRepository: MessageRepository,
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository
) {

    @MessageMapping("/matches/{matchId}/messages")
    fun sendMessage(
        @DestinationVariable matchId: Long,
        @Payload messageDTO: MessageDTO,
        @Header("email") userEmail: String
    )
        {
        if (messageDTO.senderEmail != userEmail) {
            throw IllegalAccessException("Unauthorized: Email mismatch")
        }

        val user : User = userRepository.findByEmailAndStatus(messageDTO.senderEmail, UserStatus.ACTIVE)
            ?: throw IllegalAccessException("Unauthorized: User does not exist")

        // If we do not fetch users as well, results in error due to lazy fetching. Read more here:
        // https://www.baeldung.com/hibernate-initialize-proxy-exception
        val match : Match = matchRepository.findByIdWithUsers(matchId)
            ?: throw IllegalArgumentException("Match not found")

        // Check if user is part of match.users (check comment in sendHistory)
        if (match.users.none { it.id == user.id }) {
            println("User ${user.email} is not a member of match $matchId, ignoring request.")
            return
        }

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
        val responseDTO = savedMessage.toMessageDTO()
        // This will be broadcast to subscribed clients
        messagingTemplate.convertAndSend(destination, responseDTO)
    }

    @SubscribeMapping("/matches/{matchId}/messages")
    fun sendHistory(@DestinationVariable matchId: Long,
                    @Header("email") userEmail: String) {

        val user : User = userRepository.findByEmailAndStatus(userEmail, UserStatus.ACTIVE)
            ?: throw IllegalAccessException("Unauthorized: User does not exist")

        val match : Match = matchRepository.findByIdWithUsers(matchId)
            ?: throw IllegalArgumentException("Match not found")

        // Check if user is part of match.users
        // ! user does not implement equals, so cannot use set.contains() !
        // println("Same object? " + match.users.any { it === user }) > prints false!
        if (match.users.none { it.id == user.id }) {
            println("User ${user.email} is not a member of match $matchId, ignoring request.")
            return
        }

        val messageHistory = messageRepository.findByMatchIdOrderByCreatedAtAsc(matchId)

        // Convert to DTO and send previous messages to the user who just subscribed
        messageHistory.forEach { message ->
            val messageDTO = message.toMessageDTO()
            messagingTemplate.convertAndSend("/user/matches/$matchId/messages", messageDTO)
        }
    }

}