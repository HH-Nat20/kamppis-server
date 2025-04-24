package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.Gender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertNotEquals

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Ensures cleanup after each test
class MatchRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val matchRepository: MatchRepository,
    val userProfileRepository: UserProfileRepository
){

    lateinit var user1: User
    lateinit var user2: User

    @BeforeEach
    fun setup() {
        user1 = userRepository.save(User(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            gender = Gender.MALE
        ))

        user2 = userRepository.save(User(
            firstName = "Jane",
            lastName = "Doe",
            email = "jane.doe@example.com",
            dateOfBirth = LocalDate.of(1990, 1, 1),
            gender = Gender.FEMALE
        ))
    }

    @Test
    fun `updatedAt should change when adding a user to match`() {
        val match = Match()
        matchRepository.save(match)

        val oldUpdatedAt = match.updatedAt

        userRepository.save(user1)

        Thread.sleep(1000)
        match.addUser(userRepository.findById(user1.id!!).get())
        matchRepository.save(match)

        val updatedMatch = matchRepository.findById(match.id!!).get()
        assertNotEquals(oldUpdatedAt, updatedMatch.updatedAt)
    }

    @Test
    fun `findAllByUserId returns matches containing the user`() {
        val match = Match(users = mutableSetOf(user1, user2))
        matchRepository.save(match)

        val results = matchRepository.findAllByUserId(user1.id!!)
        assertThat(results).hasSize(1)
        assertThat(results[0].users).contains(user1)
    }

    @Test
    fun `findUserProfilesThatMatchWithUser returns correct profiles`() {
        val userProfile1 = UserProfile(user = user1)
        userProfileRepository.save(userProfile1)

        val userProfile2 = UserProfile(user = user2)
        userProfileRepository.save(userProfile2)

        val managedUser1 = userRepository.findById(user1.id!!).get()
        val managedUser2 = userRepository.findById(user2.id!!).get()

        val match = Match(users = mutableSetOf(managedUser1, managedUser2))
        matchRepository.save(match)

        val results = matchRepository.findUserProfilesThatMatchWithUser(managedUser1.id!!)
        assertThat(results).extracting("user.id").contains(managedUser2.id)
    }

    @Test
    fun `findByIdWithUsers fetches match with users initialized`() {
        val match = Match(users = mutableSetOf(user1))
        matchRepository.save(match)

        val found = matchRepository.findByIdWithUsers(match.id!!)
        assertThat(found).isNotNull
        assertThat(found!!.users).isNotEmpty
    }

    @Test
    fun `findExactMatch returns match when exact users are found`() {
        val match = Match(users = mutableSetOf(user1, user2))
        matchRepository.save(match)

        val result = matchRepository.findExactMatch(setOf(user1.id!!, user2.id!!), 2)
        assertThat(result).hasSize(1)
        assertThat(result[0].users.map { it.id }).containsExactlyInAnyOrder(user1.id, user2.id)
    }
}