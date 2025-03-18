package nat20.kamppisserver.domain

import jakarta.persistence.*
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities

@Entity
@Table(name = "flat_profiles")
class FlatProfile(

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "flat_id", nullable = false)
    var flat : Flat,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Utilities::class)
    @CollectionTable(name = "flat_profiles_utilities", joinColumns = [JoinColumn(name = "profile_id")])
    @Enumerated(EnumType.STRING)
    var utilities: MutableList<Utilities>? = mutableListOf(),

    @OneToMany
    @JoinColumn(name = "flat_profile_id")
    var photos: MutableList<ProfilePhoto>? = mutableListOf(),

): Profile()

fun toFlatProfileDTO(flatProfile: FlatProfile): FlatProfileDTO {
    val flatProfileDTO = FlatProfileDTO(
        flatId = flatProfile.flat.id!!,
        name = flatProfile.flat.name,
        totalRoommates = flatProfile.flat.totalRoommates,
        location = flatProfile.flat.location,
        utilities = flatProfile.utilities,
        bio = flatProfile.bio,
        userIds = flatProfile.flat.users.map { it.id!! },
        photos = flatProfile.photos,
        id = flatProfile.id,
    )
    return flatProfileDTO
}

data class FlatProfileDTO(
    val flatId: Long,
    val name : String,
    val totalRoommates: Int,
    val location: City,
    val utilities: MutableList<Utilities>? = mutableListOf(),
    val bio: String,
    val userIds: List<Long>,
    val photos: MutableList<ProfilePhoto>?,
    val id: Long? = null,
)