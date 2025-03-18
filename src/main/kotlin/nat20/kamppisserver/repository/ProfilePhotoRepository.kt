package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.ProfilePhoto
import org.springframework.data.jpa.repository.JpaRepository

interface ProfilePhotoRepository: JpaRepository<ProfilePhoto, Long> {
}