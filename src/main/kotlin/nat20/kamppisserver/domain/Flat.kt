package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities

@Entity
@Table(name = "flats")
class Flat(

    @NotEmpty(message = "Flat name cannot be empty.")
    var name: String,

    @NotEmpty(message = "Flat description cannot be empty.")
    var description: String,

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Flat location cannot be null.")
    var location: City,

    @PositiveOrZero(message = "Total roommates must be a positive integer")
    var totalRoommates: Int,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Utilities::class)
    @CollectionTable(name = "flat_utilities", joinColumns = [JoinColumn(name = "flat_id")])
    @Enumerated(EnumType.STRING)
    var flatUtilities: MutableList<Utilities>? = mutableListOf(),

    @OneToMany(mappedBy = "flat", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var roomProfiles: MutableList<RoomProfile>? = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

) {
    fun toDTO(): FlatDTO {
        return FlatDTO(
            name = name,
            description = description,
            location = location,
            totalRoommates = totalRoommates,
            flatUtilities = flatUtilities,
            roomProfileIds = roomProfiles?.map { it.id!! },
            id = id
        )
    }

    fun toFlatDataDTO(): FlatDataDTO {
        return FlatDataDTO(
            name = name,
            description = description,
            location = location,
            totalRoommates = totalRoommates,
            flatUtilities = flatUtilities,
        )
    }
}

data class FlatDTO(
    val name: String,
    val description: String,
    val location: City,
    val totalRoommates: Int,
    val flatUtilities: MutableList<Utilities>? = mutableListOf(),
    val roomProfileIds: List<Long>? = listOf(),
    val id: Long?
)

data class FlatDataDTO(
    // Only used by UserDataExportService
    val name: String,
    val description: String,
    val location: City,
    val totalRoommates: Int,
    val flatUtilities: MutableList<Utilities>? = mutableListOf(),
)