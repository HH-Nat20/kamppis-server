package nat20.kamppisserver.service

import exception.DuplicateEmailException
import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

/**
 * Service class for User.
 */
@Service
class UserService(private val userRepository: UserRepository,
                  private val userProfileRepository: UserProfileRepository) {

    /**
     * Creates new User Profile.
     *
     * @param user the user to be created.
     * @return the created user.
     */
    fun add(user: User): User {
        // Check for all e-mails, even INACTIVE ones
        val existingUser = userRepository.findByEmail(user.email)
        if (existingUser != null) {
            throw DuplicateEmailException("User with this email already exists")
        }

        return userRepository.save(user)
    }

    /**
     * Updates given User.
     *
     * @param user the user to be updated.
     * @param id the id of the user to be updated.
     * @return the updated user.
     */
    fun update(user: User, id: Long): User {
        val updatedUser = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User with id $id not found")
        // Check for all e-mails, even INACTIVE ones
        val existingUser = userRepository.findByEmail(updatedUser.email)
        if (existingUser != null && existingUser.id != id) {
            throw DuplicateEmailException("User with this email already exists")
        }
        updatedUser.email = user.email

        return userRepository.save(updatedUser)
    }

    fun findActiveUserByEmail(email: String): User? {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
    }

    /**
     * Soft deletes given User and subsequent UserProfile.
     *
     * @param id the id of the user to be deleted.
     */
    @Transactional
    fun delete(id: Long) {
        val deletedUser = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User with id $id not found")

        val deletedUserProfile = userProfileRepository.findByUserIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User profile with id $id not found")

        deletedUser.deletedAt = LocalDateTime.now()
        deletedUser.status = UserStatus.INACTIVE
        deletedUserProfile.deletedAt = LocalDateTime.now()

        userRepository.save(deletedUser)
        userProfileRepository.save(deletedUserProfile)
    }

    /**
     * Restores deleted user.
     *
     * @param user the user to be restored.
     * @param id the id of the user to be restored.
     * @return the restored user.
     */
    @Transactional
    fun restore(user: User, id: Long): User {
        val restoredUser = userRepository.findByIdAndStatus(id, UserStatus.INACTIVE)
            ?: throw EntityNotFoundException("Deleted user with id $id not found")

        val restoredUserProfile = userProfileRepository.findByUserIdAndStatus(id, UserStatus.INACTIVE)
            ?: throw EntityNotFoundException("Deleted user profile with id $id not found")

        restoredUser.deletedAt = null
        restoredUser.status = UserStatus.ACTIVE
        restoredUserProfile.deletedAt = null

        userRepository.save(restoredUser)
        userProfileRepository.save(restoredUserProfile)

        return restoredUser
    }

}