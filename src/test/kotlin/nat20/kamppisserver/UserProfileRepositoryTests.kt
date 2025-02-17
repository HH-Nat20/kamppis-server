package nat20.kamppisserver

import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.User
import org.junit.jupiter.api.TestInstance

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * Test class for UserProfileRepository.
 */
@DataJpaTest
@Import(TestDatabaseMockDataConfiguration::class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserProfileRepositoryTests {

    @Autowired
    private lateinit var userProfileRepository: UserProfileRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should return the correct UserProfile by User-objects id`() {
        val user: User? = userRepository.findByIdOrNull(1L)
        val userProfile: UserProfile = userProfileRepository.findByUserId(user?.id)
        assertEquals("Alice", userProfile.firstName)
    }

    @Test
    fun `query should not return the user's own profile`() {
        val userProfile: UserProfile? = userProfileRepository.findByIdOrNull(1L)

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile?.user?.id,
                userProfile?.minAgePreference,
                userProfile?.maxAgePreference)

        assertFalse(userProfile in listOfUserProfiles)
    }

    @Test
    fun `query should return UserProfiles whose age fit between user's min and max age preferences`(){
        // write test here
    }


}