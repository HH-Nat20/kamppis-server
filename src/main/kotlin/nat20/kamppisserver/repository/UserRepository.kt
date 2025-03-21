package nat20.kamppisserver.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.EntityGraph

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    @EntityGraph(attributePaths = ["userProfile", "roomProfiles"])
    @Query("SELECT u FROM User u WHERE u.status = :status")
    fun findAllByStatus(@Param("status") status: UserStatus): List<User>

    @EntityGraph(attributePaths = ["userProfile", "roomProfiles"])
    @Query("SELECT u FROM User u WHERE u.id IN :ids AND u.status = :status")
    fun findAllByIdAndStatus(@Param("ids") ids: Set<Long>, @Param("status") status: UserStatus): List<User>

    @EntityGraph(attributePaths = ["userProfile", "roomProfiles"])
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.status = :status")
    fun findByIdAndStatus(@Param("id") id: Long, @Param("status") status: UserStatus): User?

    @EntityGraph(attributePaths = ["userProfile", "roomProfiles"])
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.status = :status")
    fun findByEmailAndStatus(@Param("email") email: String, @Param("status") status: UserStatus): User?
}