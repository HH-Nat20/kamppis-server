package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.UserPhoto
import org.springframework.data.jpa.repository.JpaRepository

interface UserPhotoRepository: JpaRepository<UserPhoto, Long> {
    fun findByUserProfileId(userId: Long): List<UserPhoto>

}