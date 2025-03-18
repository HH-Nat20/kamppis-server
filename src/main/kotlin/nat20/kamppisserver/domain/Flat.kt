package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import nat20.kamppisserver.domain.enums.City

@Entity
@Table(name = "flats")
class Flat(

    @NotEmpty(message = "Flat name cannot be empty.")
    var name: String,

    @NotNull(message = "Flat location cannot be null.")
    var location: City,

    @PositiveOrZero(message = "Total roommates must be a positive integer")
    var totalRoommates: Int,

    @OneToMany(mappedBy = "flats")
    var users: MutableList<User> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

) {
}