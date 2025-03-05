package nat20.kamppisserver.repository

import org.springframework.data.jpa.repository.JpaRepository

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus

interface UserRepository : JpaRepository<User, Long> {
    fun findAllByStatus(status: UserStatus): List<User>

    fun findByEmail(email: String): User?
}