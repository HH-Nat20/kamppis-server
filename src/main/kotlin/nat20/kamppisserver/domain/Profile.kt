package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PastOrPresent
import nat20.kamppisserver.domain.enums.ProfileStatus
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "profiles")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Profile(

    @NotEmpty(message = "Bio cannot be empty.")
    @Column(nullable = false)
    var bio: String = "Write bio here",

    @OneToMany
    @JoinColumn(name = "user_profile_id",)
    var photos: MutableList<ProfilePhoto> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ProfileStatus = ProfileStatus.ACTIVE,

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
    abstract fun toDTO(): ProfileDTO // Abstract function to be implemented by subclasses
}

sealed interface ProfileDTO