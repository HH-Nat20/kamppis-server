package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.setup.ContextSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.Test
import kotlin.test.assertNotEquals

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Ensures cleanup after each test
class MatchRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val flatRepository: FlatRepository,
    val roomProfileRepository: RoomProfileRepository,
    val matchRepository: MatchRepository,
    val userProfileRepository: UserProfileRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository
){

    lateinit var user1: User
    lateinit var user2: User
    lateinit var userProfile1: UserProfile
    lateinit var userProfile2: UserProfile
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
        user1 = contextSetup.user
        user2 = contextSetup.swipingUser
        userProfile1 = contextSetup.userProfile
        userProfile2 = contextSetup.swipingUserProfile
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