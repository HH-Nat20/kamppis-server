package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import nat20.kamppisserver.domain.enums.City

@Entity
@Table(name = "room_preferences")
class RoomPreference (
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @NotNull(message = "User cannot be null")
    var user: User,

    @Positive(message = "Max rent must be a positive integer")
    @Column(name = "max_rent")
    var maxRent: Int?,

    @Column(name = "has_private_room")
    var hasPrivateRoom: Boolean?,

    @Positive(message = "Max roommates in flat must be a positive integer")
    @Column(name = "max_roommates")
    var maxRoommates: Int?,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = City::class)
    @CollectionTable(name = "room_preferences_locations", joinColumns = [JoinColumn(name = "room_preferences_id")])
    @Enumerated(EnumType.STRING)
    var locationPreferences: MutableList<City>? = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    fun toRoomPreferenceDTO(): RoomPreferenceDTO {
        return RoomPreferenceDTO(
            userId = user.id,
            maxRent = maxRent,
            hasPrivateRoom = hasPrivateRoom,
            maxRoommates = maxRoommates,
            locationPreferences = locationPreferences,
            id = id
        )
    }

    fun toRoomPreferenceDataDTO(): RoomPreferenceDataDTO {
        return RoomPreferenceDataDTO(
            maxRent = maxRent,
            hasPrivateRoom = hasPrivateRoom,
            maxRoommates = maxRoommates,
            locationPreferences = locationPreferences,
        )
    }
}

data class RoomPreferenceDTO(
    val userId: Long?,
    val maxRent: Int?,
    val hasPrivateRoom: Boolean?,
    val maxRoommates: Int?,
    val locationPreferences: MutableList<City>?,
    val id: Long? = null,
)

data class RoomPreferenceDataDTO(
    // Only used by Copy of Data
    val maxRent: Int?,
    val hasPrivateRoom: Boolean?,
    val maxRoommates: Int?,
    val locationPreferences: MutableList<City>?,
)