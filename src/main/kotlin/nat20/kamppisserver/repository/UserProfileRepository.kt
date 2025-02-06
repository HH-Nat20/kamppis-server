package nat20.kamppisserver.repository

import org.springframework.data.repository.CrudRepository
import nat20.kamppisserver.domain.UserProfile

interface UserProfileRepository: CrudRepository<UserProfile, Long> {
}