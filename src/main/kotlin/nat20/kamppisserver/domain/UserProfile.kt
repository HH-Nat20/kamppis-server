package nat20.kamppisserver.domain

import jakarta.persistence.*
import nat20.kamppisserver.util.JsonStringListConverter
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "user_profiles")
class UserProfile(
    @OneToOne
    var user: User,

    var minAgePreference: Int? = null,
    var maxAgePreference: Int? = null,
    var preferredGender: Gender = Gender.NOT_IMPORTANT,

    @Convert(converter = JsonStringListConverter::class)
    var locations: List<String> = emptyList(),

    var bio: String? = null,

    var createdAt: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)