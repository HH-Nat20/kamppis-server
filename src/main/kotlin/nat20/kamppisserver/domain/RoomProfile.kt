package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PositiveOrZero
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.ProfileStatus
import java.time.LocalDateTime
import nat20.kamppisserver.service.ValidationService

@Entity
@Table(name = "room_profiles")
@ValidationService.ValidFurnishedInfo
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

    var name: String? = null,

    @PositiveOrZero(message = "Rent must be a positive integer")
    var rent: Int,

    @Column(name = "is_private_room")
    var isPrivateRoom: Boolean,

    var furnished: Boolean,

    @Column(name = "furnished_info")
    var furnishedInfo: String? = null,

    bio: String = "Write bio here", // Default value from Profile

): Profile() {
    init {
        this.bio = bio
    }

    override fun toDTO(includeUserSummary: Boolean): RoomProfileDTO {
        return RoomProfileDTO(
            userIds = users.map { it.id!! },
            users = if (includeUserSummary) users.map { it.toSummaryDTO() } else null,
            flat = flat.toDTO(),
            name = name,
            totalRoommates = flat.totalRoommates,
            location = flat.location,
            rent = rent,
            isPrivateRoom = isPrivateRoom,
            furnished = furnished,
            furnishedInfo = furnishedInfo,
            photos = photos.map { it.toProfilePhotoDTO() }
                .toMutableList(),
            bio = bio,
            id = id
        )
    }

    fun toRoomProfileRequest(): RoomProfileRequest {
        return RoomProfileRequest(
            userIds = users.map { it.id!! },
            flatId = flat.id!!,
            name = name,
            rent = rent,
            isPrivateRoom = isPrivateRoom,
            furnished = furnished,
            furnishedInfo = furnishedInfo,
            bio = bio,
            id = id
        )
    }

    fun toRoomProfileDataDTO(): RoomProfileDataDTO? {
        return RoomProfileDataDTO(
            flat = flat.toFlatDataDTO(),
            rent = rent,
            name = name,
            isPrivateRoom = isPrivateRoom,
            furnished = furnished,
            furnishedInfo = furnishedInfo,
            photos = photos.map { it.toProfilePhotoDataDTO() }
                .toMutableList(),
            bio = bio,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
        )
    }
}

data class RoomProfileDTO(
    @NotEmpty val userIds : List<Long>,
    val users: List<UserSummaryDTO>? = null,
    val flat: FlatDTO,
    val name: String? = null,
    val totalRoommates: Int,
    val location: City,
    @PositiveOrZero val rent: Int,
    val isPrivateRoom: Boolean,
    val furnished: Boolean,
    val furnishedInfo: String? = null,
    val photos: MutableList<ProfilePhotoDTO>? = mutableListOf(),
    val bio: String,
    val id: Long? = null
) : ProfileDTO

data class RoomProfileRequest(
    @NotEmpty val userIds: List<Long>,
    val flatId: Long,
    val name: String? = null,
    @PositiveOrZero val rent: Int,
    val isPrivateRoom: Boolean,
    val furnished: Boolean,
    val furnishedInfo: String?,
    val bio: String,
    val id: Long? = null
)

data class RoomProfileDataDTO(
    // Only used by Copy of Data
    val flat: FlatDataDTO,
    val name: String? = null,
    val rent: Int,
    val isPrivateRoom: Boolean,
    val furnished: Boolean,
    val furnishedInfo: String?,
    val photos: MutableList<ProfilePhotoDataDTO>? = mutableListOf(),
    val bio: String,
    val status: ProfileStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
)