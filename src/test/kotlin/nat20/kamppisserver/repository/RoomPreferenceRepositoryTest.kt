package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomPreferenceRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val roomPreferenceRepository: RoomPreferenceRepository
) {

    lateinit var user: User

    @BeforeEach
    fun setup() {
        user = userRepository.save(
            User(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                dateOfBirth = LocalDate.of(1980, 1, 1),
                gender = Gender.MALE
            )
        )
    }

    @Test
    fun `findByUserIdAndStatus returns preference when user status matches`() {
        val preference = roomPreferenceRepository.save(
            RoomPreference(
                user = user
            )
        )

        val found = roomPreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
        assertThat(found).isNotNull
        assertThat(found!!.user.id).isEqualTo(user.id)
    }

    @Test
    fun `findByUserIdAndStatus returns null when user status does not match`() {
        user.status = UserStatus.DELETED
        userRepository.save(user)

        roomPreferenceRepository.save(
            RoomPreference(
                user = user
            )
        )

        val found = roomPreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
        assertThat(found).isNull()
    }
}