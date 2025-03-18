package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities

@Entity
class RoomProfile(
    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false)
    @NotNull(message = "Profile cannot be null.")
    var profile: Profile,

    @PositiveOrZero(message = "Rent must be a positive integer")
    var rent: Int,

    var isPrivateRoom: Boolean,

    @PositiveOrZero(message = "Total roommates must be a positive integer")
    var totalRoommates: Int,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = City::class)
    @CollectionTable(name = "room_profiles_locations", joinColumns = [JoinColumn(name = "room_profile_id")])
    @Enumerated(EnumType.STRING)
    var location: MutableList<City>? = mutableListOf(),

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Utilities::class)
    @CollectionTable(name = "room_profiles_utilities", joinColumns = [JoinColumn(name = "room_profile_id")])
    @Enumerated(EnumType.STRING)
    var utilities: MutableList<Utilities>? = mutableListOf(),

    @Id
    var id: Long,
)

fun toRoomProfileDTO(roomProfile: RoomProfile): RoomProfileDTO {
    val roomProfileDTO = RoomProfileDTO(
        rent = roomProfile.rent,
        isPrivateRoom = roomProfile.isPrivateRoom,
        totalRoommates = roomProfile.totalRoommates,
        location = roomProfile.location,
        utilities = roomProfile.utilities,
        id = roomProfile.id
    )
    return roomProfileDTO
}

data class RoomProfileDTO(
    val rent: Int,
    val isPrivateRoom: Boolean,
    val totalRoommates: Int,
    val location: MutableList<City>? = mutableListOf(),
    val utilities: MutableList<Utilities>? = mutableListOf(),
    val id: Long? = null
)