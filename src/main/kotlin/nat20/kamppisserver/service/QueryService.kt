package nat20.kamppisserver.service

import nat20.kamppisserver.domain.User
import org.springframework.stereotype.Service

import nat20.kamppisserver.repository.UserRepository

@Service
class QueryService(private val userRepository: UserRepository) {

    fun findAllUsersWithSQL(): MutableIterable<User> {
        return userRepository.findAllUsersWithSQL()
    }
}