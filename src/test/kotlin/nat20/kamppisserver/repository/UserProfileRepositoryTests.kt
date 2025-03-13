package nat20.kamppisserver.repository

import nat20.kamppisserver.TestDatabaseMockDataConfiguration
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus
import org.junit.jupiter.api.TestInstance

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertFalse


/**
 * Test class for UserProfileRepository.
 */
@DataJpaTest
@Import(TestDatabaseMockDataConfiguration::class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserProfileRepositoryTests @Autowired constructor(
    val userProfileRepository: UserProfileRepository,
    val userRepository: UserRepository,
    val swipeRepository: SwipeRepository
) {
    /*
    * Here we set a base date so that our tests always calculate the same age for all users regardless of when the tests are actually run
    * If we use LocalDate.now(), tests will fail because ages will be calculated differently depending on when LocalDate.now() actually is
    */
    val testDate: LocalDate = LocalDate.of(2025, 2, 21)

    @Test
    fun `should return the correct and active UserProfile by User-objects id`() {
        val user: User = userRepository.findByIdOrNull(1L)!!
        val userProfile: UserProfile? = user.id?.let { userProfileRepository.findByUserIdAndStatus(it, UserStatus.ACTIVE) }
        if (userProfile != null) {
            assertEquals("Alice", userProfile.firstName)
        }
    }

    @Test
    fun `query should not return the user's own profile`() {
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                userProfile.minAgePreference,
                userProfile.maxAgePreference,
                userProfile.preferredGenders!!.map{it.name},
                userProfile.preferredLocations!!.map {it.name}
            )

        assertFalse(userProfile in listOfUserProfiles)
    }

    @Test
    fun `query should return UserProfiles whose age fit between user's min and max age preferences`(){
        val numberOfMatchingUserProfiles: Int = 9
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and age test fails
        swipeRepository.deleteAll()

        // Set preferred genders and locations to select all user profiles
        val preferredGenders = listOf("NOT_IMPORTANT")
        val preferredLocations = listOf("HELSINKI", "ESPOO", "VANTAA")

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                userProfile.minAgePreference,
                userProfile.maxAgePreference,
                preferredGenders,
                preferredLocations
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `query should return incorrect amount of profiles when query date is incorrect`(){
        val incorrectDate = LocalDate.of(2022, 2, 21)
        val numberOfMatchingUserProfiles: Int = 9 // matching profile count is counted using ages calculated on 2025-2-21
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and test fails
        swipeRepository.deleteAll()

        // Set preferred genders and locations to select all user profiles
        val preferredGenders = listOf("NOT_IMPORTANT")
        val preferredLocations = listOf("HELSINKI", "ESPOO", "VANTAA")

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                incorrectDate,
                userProfile.minAgePreference,
                userProfile.maxAgePreference,
                preferredGenders,
                preferredLocations
            )

        assertNotEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should return profiles that match user's preferred genders`() {
        val numberOfMatchingUserProfiles: Int = 13
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and gender test fails
        swipeRepository.deleteAll()

        // Set preferred minAge, maxAge and locations to select all user profiles
        val minAgePreference = null
        val maxAgePreference = null
        val preferredLocations = listOf("HELSINKI", "ESPOO", "VANTAA")

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                minAgePreference,
                maxAgePreference,
                userProfile.preferredGenders!!.map {it.name},
                preferredLocations
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should return profiles that match user's preferred locations`() {
        val numberOfMatchingUserProfiles = 25
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and location test fails
        swipeRepository.deleteAll()

        // Set preferred minAge, maxAge and genders to select all user profiles
        val minAgePreference = null
        val maxAgePreference = null
        val preferredGenders = listOf("NOT_IMPORTANT")

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                minAgePreference,
                maxAgePreference,
                preferredGenders,
                userProfile.preferredLocations!!.map {it.name}
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should not return profiles that have already been swiped`() {
        val numberOfMatchingUserProfiles: Int = 23
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!

        // Note that here we don't delete the swipes because that is what we want to test

        // Set preferred minAge, maxAge, genders and locations to select all user profiles
        val minAgePreference = null
        val maxAgePreference = null
        val preferredGenders = listOf("NOT_IMPORTANT")
        val preferredLocations = listOf("HELSINKI", "ESPOO", "VANTAA")

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                minAgePreference,
                maxAgePreference,
                preferredGenders,
                preferredLocations
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should return the correct amount of profiles when all criteria are used`() {
        val numberOfMatchingUserProfiles: Int = 6
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!

        // Query parameters (=user's search criteria) are selected from the user's profile
        // Swipes are taken into account
        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                userProfile.minAgePreference,
                userProfile.maxAgePreference,
                userProfile.preferredGenders!!.map { it.name },
                userProfile.preferredLocations!!.map { it.name }
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

}
