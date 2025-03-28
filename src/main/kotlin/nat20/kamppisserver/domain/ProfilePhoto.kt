package nat20.kamppisserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "profile_photos")
class ProfilePhoto(

    @ManyToOne
    @JsonIgnore
    var profile: Profile,

    @NotEmpty(message = "Url cannot be empty.")
    var url: String,

    // Is this photo on the user card (= True) or in the gallery (= False)?
    var isProfilePhoto: Boolean,

    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @PastOrPresent(message = "Update date cannot be in the future.")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    @PastOrPresent(message = "Deletion date cannot be in the future.")
    var deletedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun toProfilePhotoDTO(): ProfilePhotoDTO {
        return ProfilePhotoDTO(
            profileId = profile.id!!,
            url = url,
            isProfilePhoto = isProfilePhoto,
            id = id
        )
    }

    fun toProfilePhotoDataDTO(): ProfilePhotoDataDTO {
        return ProfilePhotoDataDTO(
            url = url,
            isProfilePhoto = isProfilePhoto,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
        )
    }
}



data class ProfilePhotoDTO(
    val profileId: Long,
    @NotEmpty val url: String,
    val isProfilePhoto: Boolean,
    val id: Long? = null
)

data class ProfilePhotoDataDTO(
    // Only used by UserDataExportService
    val url: String,
    val isProfilePhoto: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
)