package nat20.kamppisserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Entity class for User Photos.
 * @ManyToOne relationship to User Profile.
 */
@Entity
@Table(name = "user_photos")
class UserPhoto(
    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    @JsonIgnore
    var userProfile: UserProfile,

    var name: String,

    // Is this photo on the user card (= True) or in the gallery (= False)?
    var isProfilePhoto: Boolean,

    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    @UpdateTimestamp
    var deletedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
fun toUserPhotoDTO(userPhoto: UserPhoto): UserPhotoDTO {
    val userPhotoDTO: UserPhotoDTO = UserPhotoDTO(
        name = userPhoto.name,
        isProfilePhoto = userPhoto.isProfilePhoto,
        id = userPhoto.id!!
    )

    return userPhotoDTO
}

data class UserPhotoDTO(
    val name: String,
    val isProfilePhoto: Boolean,
    val id: Long
)