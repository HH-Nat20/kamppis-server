package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PositiveOrZero
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities

@Entity
@Table(name = "room_profiles")
class RoomProfile(

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "room_profiles_users",
        joinColumns = [JoinColumn(name = "room_profile_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    @NotEmpty(message = "A room profile must have at least one user.")
    var users: MutableList<User>,

    @ManyToOne
    @JoinColumn(name = "flat_id", nullable = false)
    var flat: Flat,

    @PositiveOrZero(message = "Rent must be a positive integer")
    var rent: Int,

    @Column(name = "is_private_room")
    var isPrivateRoom: Boolean,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Utilities::class)
    @CollectionTable(name = "room_profiles_utilities", joinColumns = [JoinColumn(name = "profile_id")])
    @Enumerated(EnumType.STRING)
    var roomUtilities: MutableList<Utilities>? = mutableListOf(),

    bio: String = "Write bio here", // Default value from Profile

): Profile() {
    init {
        this.bio = bio
    }

    override fun toDTO(): RoomProfileDTO {
        return RoomProfileDTO(
            userIds = users.map { it.id!! },
            flat = flat.toDTO(),
            totalRoommates = flat.totalRoommates,
            location = flat.location,
            rent = rent,
            isPrivateRoom = isPrivateRoom,
            roomUtilities = roomUtilities,
            photos = photos.map { toProfilePhotoDTO(it) }.toMutableList(),
            bio = bio,
            id = id
        )
    }
}

data class RoomProfileDTO(
    val userIds : List<Long>,
    val flat: FlatDTO,
    val totalRoommates: Int,
    val location: City,
    val rent: Int,
    val isPrivateRoom: Boolean,
    val roomUtilities: MutableList<Utilities>? = mutableListOf(),
    val photos: MutableList<ProfilePhotoDTO>? = mutableListOf(),
    val bio: String,
    val id: Long? = null
) : ProfileDTO

data class RoomProfileRequest(
    val userIds: List<Long>,
    val flatId: Long,
    val rent: Int,
    val isPrivateRoom: Boolean,
    val roomUtilities: MutableList<Utilities>? = mutableListOf(),
    val bio: String,
    val id: Long? = null
)