package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.UserDTO
import nat20.kamppisserver.domain.UserDataDTO
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.*
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

/**
 * Service class for GDPR-compliant Copy of Information functionality. The User
 * receives a copy of
 * - profile information
 * - preferences and settings
 * - any data from related tables
 * - metadata (createdAt, latest update, etc.)
 * - logs
 * Does NOT return information that poses a security risk, e.g. IDs.
 */
class UserDataExportService(private val userRepository: UserRepository,
                            private val userProfileRepository: UserProfileRepository,
                            private val roomProfileRepository: RoomProfileRepository,
) {
/**
    fun getCopyOfUserData(id: Long): UserDataDTO {
        userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User with id $id not found")
        userProfileRepository.findByUserIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User profile with user id $id not found")
    }
*/
}