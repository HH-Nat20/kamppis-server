package ohjelmistoprojekti2.kamppis_server.service

import ohjelmistoprojekti2.kamppis_server.domain.User
import org.springframework.stereotype.Service

import ohjelmistoprojekti2.kamppis_server.repository.UserRepository

@Service
class QueryService(val userRepository: UserRepository) {

    fun findAllUsersFromRepository(): List<User> {
        return userRepository.findAll().toList();
    }
}