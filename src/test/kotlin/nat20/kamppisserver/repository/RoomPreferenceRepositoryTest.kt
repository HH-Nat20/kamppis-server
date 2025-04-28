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
class RoomPreferenceRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository,
    val flatRepository: FlatRepository,
    val roomProfileRepository: RoomProfileRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository
) {

    lateinit var user: User
    lateinit var roomPreference: RoomPreference
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
        roomPreference = contextSetup.roomPreference
    }

    @Test
    fun `findByUserIdAndStatus returns preference when user status matches`() {
        val found = roomPreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
        assertThat(found).isNotNull
        assertThat(found!!.user.id).isEqualTo(user.id)
    }

    @Test
    fun `findByUserIdAndStatus returns null when user status does not match`() {
        user.status = UserStatus.DELETED
        userRepository.save(user)

        val found = roomPreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
        assertThat(found).isNull()
    }
}