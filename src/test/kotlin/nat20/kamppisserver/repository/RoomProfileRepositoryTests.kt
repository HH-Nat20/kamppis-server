package nat20.kamppisserver.repository

import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.UserStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.*

/**
 * Test class for RoomProfileRepository.
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class RoomProfileRepositoryTests @Autowired constructor(
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roomProfileRepository: RoomProfileRepository,
    val swipeRepository: SwipeRepository
){
    /*
    * Here we declare variables that are used in every test
    */
    lateinit var user: User
    lateinit var userProfile: UserProfile
    lateinit var roomPreference: RoomPreference

    @BeforeEach
    fun testVariableSetUp() {
        // We find our test user
        user = userRepository.findByIdAndStatus(27L, UserStatus.ACTIVE)
            ?: fail("❌ Expected User but found null")
        // We find our test user's user profile
        userProfile = userProfileRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
            ?: fail("❌ Expected UserProfile but found null")
        // We find our test user's room preferences
        roomPreference = roomPreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
            ?: fail("❌ Expected RoomPreference but found null")
    }

    @Test
    fun `should return RoomProfiles whose rent is below or at user's max rent criteria`() {
        val numberOfMatchingRoomProfiles: Int = 18

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and max rent test fails
        swipeRepository.deleteAll()

        // Set other criteria to null so that they do not affect the max rent filtering
        val hasPrivateRoom = null;
        val maxRoommates = null
        val locationPreferences = null

        val listOfRoomProfiles: Iterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                userProfile.id!!,
                roomPreference.maxRent,
                hasPrivateRoom,
                maxRoommates,
                locationPreferences
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }

    @Test
    fun `should return RoomProfiles that match user's room privacy criteria`() {
        val numberOfMatchingRoomProfiles: Int = 14

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and privacy criteria test fails
        swipeRepository.deleteAll()

        // Set other criteria to null so that they do not affect the room privacy filtering
        val maxRent = null
        val maxRoommates = null
        val locationPreferences = null

        val listOfRoomProfiles: Iterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                userProfile.id!!,
                maxRent,
                roomPreference.hasPrivateRoom,
                maxRoommates,
                locationPreferences
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }

    @Test
    fun `should return RoomProfiles for flats whose total roommate count is below or at user's max roommate criteria`() {
        val numberOfMatchingRoomProfiles: Int = 14

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and max roommate test fails
        swipeRepository.deleteAll()

        // Set other criteria to null so that they do not affect the max roommate filtering
        val maxRent = null
        val hasPrivateRoom = null
        val locationPreferences = null

        val listOfRoomProfiles: Iterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                userProfile.id!!,
                maxRent,
                hasPrivateRoom,
                roomPreference.maxRoommates,
                locationPreferences
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }

    @Test
    fun `should return RoomProfiles for flats whose location is in user's location preferences`() {
        val numberOfMatchingRoomProfiles: Int = 18

        // We delete the swipes because we don't want them to interfere the test
        // If not, the query filters out swiped profiles and location preference test fails
        swipeRepository.deleteAll()

        // Set other criteria to null so that they do not affect the location filtering
        val maxRent = null
        val hasPrivateRoom = null
        val maxRoommates = null

        val listOfRoomProfiles: Iterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                userProfile.id!!,
                maxRent,
                hasPrivateRoom,
                maxRoommates,
                roomPreference.locationPreferences!!.map {it.name }.toMutableList()
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }

    @Test
    fun `should not return profiles that have already been swiped`(){
        val numberOfMatchingRoomProfiles: Int = 16

        // Note that here we don't delete the swipes because that is what we want to test

        // Set other criteria to null so that they do not affect the swipe filtering
        val maxRent = null
        val hasPrivateRoom = null
        val maxRoommates = null
        val locationPreferences = null

        val listOfRoomProfiles: Iterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                userProfile.id!!,
                maxRent,
                hasPrivateRoom,
                maxRoommates,
                locationPreferences
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }

    @Test
    fun `should return the correct amount of room profiles when all criteria are used`() {
        val numberOfMatchingRoomProfiles: Int = 10

        // Query parameters (=user's search criteria) are selected from the user's room preferences
        // Swipes are taken into account
        val listOfRoomProfiles: Iterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                userProfile.id!!,
                roomPreference.maxRent,
                roomPreference.hasPrivateRoom,
                roomPreference.maxRoommates,
                roomPreference.locationPreferences?.map {it.name}
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }
}