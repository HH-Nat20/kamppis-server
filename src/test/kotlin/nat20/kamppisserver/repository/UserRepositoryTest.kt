package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.setup.ContextSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest @Autowired constructor(
    val userProfileRepository: UserProfileRepository,
    val roomProfileRepository: RoomProfileRepository,
    val flatRepository: FlatRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository,
    val userRepository: UserRepository
) {

    lateinit var activeUser: User
    lateinit var inactiveUser: User
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
        activeUser = contextSetup.user
        inactiveUser = contextSetup.deletedUser
    }

    @Test
    fun `findByEmail returns user`() {
        val result = userRepository.findByEmail("john.doe@example.com")
        assertThat(result).isNotNull
        assertThat(result?.email).isEqualTo("john.doe@example.com")
    }

    @Test
    fun `findAllByStatus returns active users`() {
        val results = userRepository.findAllByStatus(UserStatus.ACTIVE)
        assertThat(results).contains(activeUser)
    }

    @Test
    fun `findAllByIdAndStatus returns correct users`() {
        val ids = setOf(activeUser.id!!)
        val results = userRepository.findAllByIdAndStatus(ids, UserStatus.ACTIVE)
        assertThat(results).hasSize(1).extracting("email").contains("john.doe@example.com")
    }

    @Test
    fun `findByIdAndStatus returns correct user`() {
        val result = userRepository.findByIdAndStatus(activeUser.id!!, UserStatus.ACTIVE)
        assertThat(result).isNotNull
        assertThat(result?.email).isEqualTo("john.doe@example.com")
    }

    @Test
    fun `findByEmailAndStatus returns correct user`() {
        val result = userRepository.findByEmailAndStatus("john.doe@example.com", UserStatus.ACTIVE)
        assertThat(result).isNotNull
        assertThat(result?.email).isEqualTo("john.doe@example.com")
    }

    @Test
    fun `findAllByStatusAndDeletedAtBefore returns users with deletedAt`() {
        val now = LocalDateTime.now()

        val results = userRepository.findAllByStatusAndDeletedAtBefore(UserStatus.INACTIVE, now)
        assertThat(results).extracting("email").contains("deleted.doe@example.com")
    }

    @Test
    fun `findAllByStatusAndEmailEndsWith returns users with email suffix`() {
        val results = userRepository.findAllByStatusAndEmailEndsWith(UserStatus.ACTIVE, "@example.com")
        assertThat(results).extracting("email").contains("john.doe@example.com")
    }
}