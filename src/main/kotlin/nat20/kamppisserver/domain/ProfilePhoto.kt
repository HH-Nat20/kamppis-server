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
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Profile cannot be null.")
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
)
fun toProfilePhotoDTO(profilePhoto: ProfilePhoto): ProfilePhotoDTO {
    val profilePhotoDTO: ProfilePhotoDTO = ProfilePhotoDTO(
        url = profilePhoto.url,
        isProfilePhoto = profilePhoto.isProfilePhoto,
        id = profilePhoto.id
    )

    return profilePhotoDTO
}

data class ProfilePhotoDTO(
    @NotEmpty val url: String,
    val isProfilePhoto: Boolean,
    val id: Long? = null
)