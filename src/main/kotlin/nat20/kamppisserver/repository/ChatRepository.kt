package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<Chat, Long> {
}