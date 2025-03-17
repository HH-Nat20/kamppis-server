package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities

@Entity
class RoomProfile(
    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false)
    @NotNull(message = "Profile cannot be null.")
    var profile: Profile,

    @Positive(message = "Rent must be a positive integer or null")
    var rent: Int? = null,

    var isPrivateRoom: Boolean? = null,

    @Positive(message = "Total roommates must be a positive integer or null")
    var totalRoommates: Int? = null,

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
    val rent: Int? = null,
    val isPrivateRoom: Boolean? = null,
    val totalRoommates: Int? = null,
    val location: MutableList<City>? = mutableListOf(),
    val utilities: MutableList<Utilities>? = mutableListOf(),
    val id: Long? = null
)