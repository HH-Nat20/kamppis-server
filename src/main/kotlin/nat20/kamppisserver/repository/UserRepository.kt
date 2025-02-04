package nat20.kamppisserver.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

import nat20.kamppisserver.domain.User

interface UserRepository : CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM \"users\"", nativeQuery = true)
    fun findAllUsersWithSQL(): MutableIterable<User>

}