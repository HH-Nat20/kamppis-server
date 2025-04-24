package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.ProfileStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfileRepositoryTest @Autowired constructor(
    val profileRepository: ProfileRepository,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository
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
    fun `findByIdAndStatus returns profile if status matches`() {
        val userProfile = userProfileRepository.save(
            UserProfile(
                user = user,
                status = ProfileStatus.ACTIVE
            )
        )

        val found = profileRepository.findByIdAndStatus(userProfile.id!!)
        assertThat(found).isNotNull
        assertThat(found!!.status).isEqualTo(ProfileStatus.ACTIVE)
    }

    @Test
    fun `findByIdAndStatus returns null if status does not match`() {
        val userProfile = userProfileRepository.save(UserProfile(user = user))

        userProfile.status = ProfileStatus.INACTIVE
        userProfileRepository.save(userProfile)

        val found = profileRepository.findByIdAndStatus(userProfile.id!!)
        assertThat(found).isNull()
    }
}