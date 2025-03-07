package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.domain.toUserProfileDTO
import nat20.kamppisserver.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

/**
 * Service class for User.
 */
@Service
class UserService(private val repository: UserRepository) {

    /**
     * Creates new User Profile.
     *
     * @param user the user to be created.
     * @return the created user.
     */
    fun add(user: User): User {
        val existingUser = repository.findByEmail(user.email)
        if (existingUser != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists")
        }

        return repository.save(user)
    }

    /**
     * Updates given User.
     *
     * @param user the user to be updated.
     * @param id the id of the user to be updated.
     * @return the updated user.
     */
    fun update(user: User, id: Long): User {
        val updatedUser = repository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("User with id $id not found")
        val existingUser = repository.findByEmail(updatedUser.email)
        if (existingUser != null && existingUser.id != id) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists")
        }
        updatedUser.email = user.email

        return repository.save(updatedUser)
    }

    fun findUserByEmail(email: String): User? {
        return repository.findByEmail(email)
    }
}