package nat20.kamppisserver.service

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserStatus
import nat20.kamppisserver.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Service class for User. Specifically used for chat functionality atm.
 */
@Service
class UserService(private val repository: UserRepository) {

    fun saveUser(user: User) {
        user.status = UserStatus.ONLINE
        repository.save(user)
    }

    fun disconnect(user: User) {
        val savedUser = repository.findByIdOrNull(user.id)
        if (savedUser != null) {
            savedUser.status = UserStatus.OFFLINE
            repository.save(savedUser)
        }
    }

    fun findConnectedUsers(): List<User> {
        return repository.findAllByStatus(UserStatus.ONLINE)
    }

    fun findUserByEmail(email: String): User? {
        return repository.findByEmail(email)
    }

}