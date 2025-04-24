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
import java.time.LocalDateTime
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest @Autowired constructor(
    val userRepository: UserRepository
) {

    lateinit var activeUser: User
    lateinit var inactiveUser: User

    @BeforeEach
    fun setup() {
        activeUser = userRepository.save(
            User(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                dateOfBirth = LocalDate.of(1980, 1, 1),
                gender = Gender.MALE
            )
        )

        inactiveUser = User(
                firstName = "Jane",
                lastName = "Doe",
                email = "jane.doe@example.com",
                dateOfBirth = LocalDate.of(1990, 1, 1),
                gender = Gender.FEMALE
            )

        val now = LocalDateTime.now()
        inactiveUser.status = UserStatus.INACTIVE
        inactiveUser.deletedAt = now.minusDays(1)
        userRepository.save(inactiveUser)
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
        assertThat(results).extracting("email").contains("jane.doe@example.com")
    }

    @Test
    fun `findAllByStatusAndEmailEndsWith returns users with email suffix`() {
        val results = userRepository.findAllByStatusAndEmailEndsWith(UserStatus.ACTIVE, "@example.com")
        assertThat(results).extracting("email").contains("john.doe@example.com")
    }
}