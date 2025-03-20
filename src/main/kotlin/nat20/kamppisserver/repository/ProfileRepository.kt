package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Profile
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository: JpaRepository<Profile, Long> {
    // fun findByUserId(userId: Long): UserProfile? // This does not work as expected (OneToOne relation between user and user profile and ManyToMany with room profile)
}