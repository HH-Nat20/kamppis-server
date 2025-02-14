package nat20.kamppisserver.service

import nat20.kamppisserver.domain.Message
import nat20.kamppisserver.repository.MessageRepository
import org.springframework.stereotype.Service

/**
 * Service class for Message.
 */
@Service
class MessageService(private val repository: MessageRepository) {
    fun createMessage(message: Message): Message {
        return repository.save(message)
    }
}