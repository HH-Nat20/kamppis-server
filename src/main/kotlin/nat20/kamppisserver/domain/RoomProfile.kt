package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.PositiveOrZero
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities

@Entity
@Table(name = "room_profiles")
class RoomProfile(

    @ManyToOne
    @JoinColumn(name = "flat_id", nullable = false)
    var flat: Flat,

    @PositiveOrZero(message = "Rent must be a positive integer")
    var rent: Int,

    var isPrivateRoom: Boolean,


    @ElementCollection(fetch = FetchType.EAGER, targetClass = Utilities::class)
    @CollectionTable(name = "room_profiles_utilities", joinColumns = [JoinColumn(name = "profile_id")])
    @Enumerated(EnumType.STRING)
    var utilities: MutableList<Utilities>? = mutableListOf(),

): Profile()

fun toRoomProfileDTO(roomProfile: RoomProfile): RoomProfileDTO {
    val roomProfileDTO = RoomProfileDTO(
        rent = roomProfile.rent,
        isPrivateRoom = roomProfile.isPrivateRoom,
        totalRoommates = roomProfile.flat.totalRoommates,
        location = roomProfile.flat.location,
        utilities = roomProfile.utilities,
        bio = roomProfile.bio,
        flatId = roomProfile.flat.id!!,
        id = roomProfile.id
    )
    return roomProfileDTO
}

data class RoomProfileDTO(
    val rent: Int,
    val isPrivateRoom: Boolean,
    val totalRoommates: Int,
    val location: City,
    val utilities: MutableList<Utilities>? = mutableListOf(),
    val bio: String,
    val flatId: Long,
    val id: Long? = null
)