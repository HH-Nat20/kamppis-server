package nat20.kamppisserver.api

import nat20.kamppisserver.domain.MessageDTO
import nat20.kamppisserver.service.MessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.web.bind.annotation.*
import java.security.Principal

/**
 * Controller for Message.
 */
@RestController
class MessageController(
    private val messageService: MessageService
) {

    @MessageMapping("/matches/{matchId}/messages")
    fun sendMessage(@DestinationVariable matchId: Long, @Payload messageDTO: MessageDTO, principal: Principal) {
        messageService.sendMessage(matchId, messageDTO, principal)
    }

    @SubscribeMapping("/matches/{matchId}/messages")
    fun sendHistory(@DestinationVariable matchId: Long, principal: Principal) {
        messageService.sendHistory(matchId, principal)
    }
}