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

    @OneToMany
    @JoinColumn(name = "room_profile_id") // Creates a foreign key column in ProfilePhoto
    var photos: MutableList<ProfilePhoto>? = mutableListOf(),

): Profile()

fun toRoomProfileDTO(roomProfile: RoomProfile): RoomProfileDTO {
    val roomProfileDTO = RoomProfileDTO(
        flatId = roomProfile.flat.id!!,
        totalRoommates = roomProfile.flat.totalRoommates,
        location = roomProfile.flat.location,
        rent = roomProfile.rent,
        isPrivateRoom = roomProfile.isPrivateRoom,
        utilities = roomProfile.utilities,
        photos = roomProfile.photos,
        bio = roomProfile.bio,
        id = roomProfile.id
    )
    return roomProfileDTO
}

data class RoomProfileDTO(
    val flatId: Long,
    val totalRoommates: Int,
    val location: City,
    val rent: Int,
    val isPrivateRoom: Boolean,
    val utilities: MutableList<Utilities>? = mutableListOf(),
    val photos: MutableList<ProfilePhoto>? = mutableListOf(),
    val bio: String,
    val id: Long? = null
)