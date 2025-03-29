package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.service.ValidationService

@Entity
@Table(name = "roommate_preferences")
@ValidationService.ValidAgePreferences // Validates that minAgePreference < maxAgePreference
class RoommatePreference (
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @NotNull(message = "User cannot be null")
    var user: User,

    @PositiveOrZero(message = "Min age preference must be a positive integer or zero")
    @Column(name= "min_age_preference")
    var minAgePreference: Int?,

    @Positive(message = "Min age preference must be a positive integer")
    @Column(name= "max_age_preference")
    var maxAgePreference: Int?,

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Gender::class)
    @CollectionTable(name = "roommate_preferences_gender", joinColumns = [JoinColumn(name = "roommate_preferences_id")])
    @Enumerated(EnumType.STRING)
    var genderPreferences: MutableList<Gender>? = mutableListOf(),

    @ElementCollection(fetch = FetchType.EAGER, targetClass = City::class)
    @CollectionTable(name = "roommate_preferences_location", joinColumns = [JoinColumn(name = "roommate_preferences_id")])
    @Enumerated(EnumType.STRING)
    var locationPreferences: MutableList<City>? = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    fun toRoommatePreferenceDTO(): RoommatePreferenceDTO {
        return RoommatePreferenceDTO(
            userId = user.id,
            minAgePreference = minAgePreference,
            maxAgePreference = maxAgePreference,
            genderPreferences = genderPreferences,
            locationPreferences = locationPreferences,
            id = id
        )
    }

    fun toRoommatePreferenceDataDTO(): RoommatePreferenceDataDTO {
        return RoommatePreferenceDataDTO(
            minAgePreference = minAgePreference,
            maxAgePreference = maxAgePreference,
            genderPreferences = genderPreferences,
            locationPreferences = locationPreferences,
        )
    }
}

data class RoommatePreferenceDTO(
    val userId: Long?,
    val minAgePreference: Int?,
    val maxAgePreference: Int?,
    val genderPreferences: MutableList<Gender>? = mutableListOf(),
    val locationPreferences: MutableList<City>? = mutableListOf(),
    val id: Long? = null,
)

data class RoommatePreferenceDataDTO(
    // Only used by Copy of Data
    val minAgePreference: Int?,
    val maxAgePreference: Int?,
    val genderPreferences: MutableList<Gender>? = mutableListOf(),
    val locationPreferences: MutableList<City>? = mutableListOf(),
)