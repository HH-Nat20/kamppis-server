package nat20.kamppisserver.repository

import nat20.kamppisserver.TestDatabaseMockDataConfiguration
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.User
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
    val userRepository: UserRepository
) {
    /*
    * Here we set a base date so that our tests always calculate the same age for all users regardless of when the tests are actually run
    * If we use LocalDate.now(), tests will fail because ages will be calculated differently depending on when LocalDate.now() actually is
    */
    val testDate: LocalDate = LocalDate.of(2025, 2, 21)

    @Test
    fun `should return the correct UserProfile by User-objects id`() {
        val user: User = userRepository.findByIdOrNull(1L)!!
        val userProfile: UserProfile = userProfileRepository.findByUserId(user.id)
        assertEquals("Alice", userProfile.firstName)
    }

    @Test
    fun `query should not return the user's own profile`() {
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!
        val preferredGenders = userProfile.preferredGenders!!.map{it.name}
        val preferredLocations = userProfile.locations!!.map {it.name}

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                userProfile.minAgePreference,
                userProfile.maxAgePreference,
                preferredGenders,
                preferredLocations
            )

        assertFalse(userProfile in listOfUserProfiles)
    }

    @Test
    fun `query should return UserProfiles whose age fit between user's min and max age preferences`(){
        val numberOfMatchingUserProfiles: Int = 6
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!
        val preferredGenders = listOf("NOT_IMPORTANT")
        val locations = listOf("HELSINKI", "ESPOO", "VANTAA")

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                userProfile.minAgePreference,
                userProfile.maxAgePreference,
                preferredGenders,
                locations
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `query should return incorrect amount of profiles when query date is incorrect`(){
        val incorrectDate = LocalDate.of(2022, 2, 21)
        val numberOfMatchingUserProfiles: Int = 6 // matching profile count is counted using ages calculated on 2025-2-21
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!
        val preferredGenders = listOf("NOT_IMPORTANT")
        val locations = listOf("HELSINKI", "ESPOO", "VANTAA")

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                incorrectDate,
                userProfile.minAgePreference,
                userProfile.maxAgePreference,
                preferredGenders,
                locations
            )

        assertNotEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should return profiles that match user's preferred genders`() {
        val numberOfMatchingUserProfiles: Int = 4
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!
        val preferredGenders = userProfile.preferredGenders!!.map {it.name}
        val locations = listOf("HELSINKI", "ESPOO", "VANTAA")

        val listOfUserProfiles: MutableIterable<UserProfile> =
            userProfileRepository.findUserProfilesThatMeetCriteria(
                userProfile.user.id,
                testDate,
                userProfile.minAgePreference,
                userProfile.maxAgePreference,
                preferredGenders,
                locations
            )

        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }

    @Test
    fun `should return profiles that match user's preferred locations`() {
        val numberOfMatchingUserProfiles = 23
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!
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
                userProfile.locations!!.map {it.name}
            )

        println(listOfUserProfiles)
        println(listOfUserProfiles.count())
        assertEquals(numberOfMatchingUserProfiles, listOfUserProfiles.count())
    }
}
