package nat20.kamppisserver.service

import nat20.kamppisserver.domain.User
import org.springframework.stereotype.Service

import nat20.kamppisserver.repository.UserRepository

@Service
class QueryService(val userRepository: UserRepository) {

    fun findAllUsersFromRepository(): List<User> {
        return userRepository.findAll().toList();
    }
}