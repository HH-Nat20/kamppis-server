package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.ProfileStatus
import nat20.kamppisserver.setup.ContextSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfileRepositoryTest @Autowired constructor(
    val profileRepository: ProfileRepository,
    val userRepository: UserRepository,
    val flatRepository: FlatRepository,
    val roomProfileRepository: RoomProfileRepository,
    val userProfileRepository: UserProfileRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository
) {

    lateinit var user: User
    lateinit var userProfile: UserProfile
    lateinit var contextSetup: ContextSetup

    @BeforeEach
    fun init() {
        contextSetup = ContextSetup(
            userRepository,
            userProfileRepository,
            flatRepository,
            roomProfileRepository,
            roomPreferenceRepository,
            roommatePreferenceRepository
        )
        contextSetup.setup()
        user = contextSetup.user
        userProfile = contextSetup.userProfile
    }

    @Test
    fun `findByIdAndStatus returns profile if status matches`() {
        val found = profileRepository.findByIdAndStatus(userProfile.id!!)
        assertThat(found).isNotNull
        assertThat(found!!.status).isEqualTo(ProfileStatus.ACTIVE)
    }

    @Test
    fun `findByIdAndStatus returns null if status does not match`() {
        userProfile.status = ProfileStatus.INACTIVE
        userProfileRepository.save(userProfile)

        val found = profileRepository.findByIdAndStatus(userProfile.id!!)
        assertThat(found).isNull()
    }
}