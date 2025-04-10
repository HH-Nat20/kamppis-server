package nat20.kamppisserver.repository

import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.RoommatePreference
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import kotlin.test.*

/**
 * Test class for UserProfileRepository.
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class UserProfileRepositoryTests @Autowired constructor(
    val userProfileRepository: UserProfileRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository,
    val userRepository: UserRepository,
    val swipeRepository: SwipeRepository
) {

    /*
    * Here we declare variables that are used in every test
    */
    lateinit var testDate: LocalDate
    lateinit var user: User
    lateinit var userProfile: UserProfile
    lateinit var roommatePreference: RoommatePreference
    lateinit var pageRequest: PageRequest

    @BeforeEach
    fun testVariableSetUp() {
        // Here we set a base date so that our tests always calculate the same age for all users regardless of when the tests are actually run
        testDate= LocalDate.of(2025, 2, 21)
        // We find our test user
        user = userRepository.findByIdAndStatus(1L, UserStatus.ACTIVE)
            ?: fail("❌ Expected User but found null")
        // We find our test user's user profile
        userProfile = userProfileRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
            ?: fail("❌ Expected UserProfile but found null")
        roommatePreference= roommatePreferenceRepository
            .findByUserIdAndStatus(1L, UserStatus.ACTIVE)
            ?: fail("❌ Expected RoommatePreference but found null")
        pageRequest = PageRequest.of(0, 25)
    }

    @Test
    fun `query should not return the user's own profile`() {
        val listOfUserProfiles: Iterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                pageRequest,
                userProfile.id!!,
                testDate,
                roommatePreference.minAgePreference,
                roommatePreference.maxAgePreference,
                roommatePreference.genderPreferences?.map { it.name },
                roommatePreference.locationPreferences?.map { it.name }
            )

        assertFalse(userProfile in listOfUserProfiles)
    }

    @Test
    fun `query should return UserProfiles whose age fit between user's min and max age preferences`(){
        val numberOfMatchingUserProfiles: Int = 9

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and age test fails
        swipeRepository.deleteAll()

        // Set preferred genders and locations to select all user profiles
        val preferredGenders = null
        val preferredLocations = null

        val listOfUserProfiles: Iterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                pageRequest,
                userProfile.id!!,
                testDate,
                roommatePreference.minAgePreference,
                roommatePreference.maxAgePreference,
                preferredGenders,
                preferredLocations
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `query should return incorrect amount of profiles when query date is incorrect`(){
        val incorrectDate = LocalDate.of(2022, 2, 21)
        val numberOfMatchingUserProfiles: Int = 9 // matching profile count is counted using ages calculated on 2025-2-21

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and test fails
        swipeRepository.deleteAll()

        // Set preferred genders and locations to select all user profiles
        val preferredGenders = null
        val preferredLocations = null

        val listOfUserProfiles: Iterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                pageRequest,
                userProfile.id!!,
                incorrectDate,
                roommatePreference.minAgePreference,
                roommatePreference.maxAgePreference,
                preferredGenders,
                preferredLocations
            )

        assertNotEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should return profiles that match user's preferred genders`() {
        val numberOfMatchingUserProfiles: Int = 14

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and gender test fails
        swipeRepository.deleteAll()

        // Set preferred minAge, maxAge and locations to select all user profiles
        val minAgePreference = null
        val maxAgePreference = null
        val preferredLocations = null

        val listOfUserProfiles: Iterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                pageRequest,
                userProfile.id!!,
                testDate,
                minAgePreference,
                maxAgePreference,
                roommatePreference.genderPreferences?.map {it.name},
                preferredLocations
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should return profiles that match user's preferred locations`() {
        val numberOfMatchingUserProfiles = 25

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and location test fails
        swipeRepository.deleteAll()

        // Set preferred minAge, maxAge and genders to select all user profiles
        val minAgePreference = null
        val maxAgePreference = null
        val preferredGenders = null

        val listOfUserProfiles: Iterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                pageRequest,
                userProfile.id!!,
                testDate,
                minAgePreference,
                maxAgePreference,
                preferredGenders,
                roommatePreference.locationPreferences?.map {it.name}
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should not return profiles that have already been swiped`() {
        val numberOfMatchingUserProfiles: Int = 23

        // Note that here we don't delete the swipes because that is what we want to test

        // Set preferred minAge, maxAge, genders and locations to select all user profiles
        val minAgePreference = null
        val maxAgePreference = null
        val preferredGenders = null
        val preferredLocations = null

        val listOfUserProfiles: Iterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                pageRequest,
                userProfile.id!!,
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
        val numberOfMatchingUserProfiles: Int = 7

        // Query parameters (=user's search criteria) are selected from the user's profile
        // Swipes are taken into account
        val listOfUserProfiles: Iterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                pageRequest,
                userProfile.id!!,
                testDate,
                roommatePreference.minAgePreference,
                roommatePreference.maxAgePreference,
                roommatePreference.genderPreferences?.map { it.name },
                roommatePreference.locationPreferences?.map { it.name }
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }
}
