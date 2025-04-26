package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.setup.ContextSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoommatePreferenceRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository,
    val flatRepository: FlatRepository,
    val roomProfileRepository: RoomProfileRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository,
    val roomPreferenceRepository: RoomPreferenceRepository
) {

    lateinit var user: User
    lateinit var roommatePreference: RoommatePreference
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
        roommatePreference = contextSetup.roommatePreference
    }

    @Test
    fun `findByUserIdAndStatus returns preference when user status matches`() {
        val found = roommatePreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
        assertThat(found).isNotNull
        assertThat(found!!.user.id).isEqualTo(user.id)
    }

    @Test
    fun `findByUserIdAndStatus returns null when user status does not match`() {
        user.status = UserStatus.DELETED
        userRepository.save(user)

        val found = roommatePreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
        assertThat(found).isNull()
    }
}