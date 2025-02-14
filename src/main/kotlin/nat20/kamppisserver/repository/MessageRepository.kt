package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<Message, Long> {
}