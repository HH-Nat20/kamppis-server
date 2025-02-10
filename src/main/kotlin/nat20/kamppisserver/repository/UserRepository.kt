package nat20.kamppisserver.repository

import org.springframework.data.jpa.repository.JpaRepository

import nat20.kamppisserver.domain.User

interface UserRepository : JpaRepository<User, Long> {
}