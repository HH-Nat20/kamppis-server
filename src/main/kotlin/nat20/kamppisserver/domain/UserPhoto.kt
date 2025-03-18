package nat20.kamppisserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "user_photos")
class UserPhoto(
    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "User profile cannot be null.")
    var userProfile: UserProfile,

    @NotEmpty(message = "Name cannot be empty.")
    var name: String,

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
fun toUserPhotoDTO(userPhoto: UserPhoto): UserPhotoDTO {
    val userPhotoDTO: UserPhotoDTO = UserPhotoDTO(
        name = userPhoto.name,
        isProfilePhoto = userPhoto.isProfilePhoto,
        userId = userPhoto.userProfile.id!!,
        id = userPhoto.id!!
    )

    return userPhotoDTO
}

data class UserPhotoDTO(
    @NotEmpty val name: String,
    val isProfilePhoto: Boolean,
    val userId: Long,
    val id: Long? = null
)