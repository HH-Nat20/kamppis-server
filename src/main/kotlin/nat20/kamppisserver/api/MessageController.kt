package nat20.kamppisserver.api

import nat20.kamppisserver.domain.Message
import nat20.kamppisserver.service.MessageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for Message.
 *
 * Lacking GET all, POST, PUT, and DELETE methods. All Messages belonging to a
 * specified Chat are fetched with GET. A Message can be POST, PUT, or DELETEd
 * by the User (sender).
 */
@RestController
@RequestMapping("/api/message")
class MessageController(private val service: MessageService) {
    @PostMapping
    fun createMessage(@RequestBody message: Message): ResponseEntity<Message> {
        val createdMessage = service.createMessage(message)
        return ResponseEntity<Message>(createdMessage, HttpStatus.CREATED)
    }
}