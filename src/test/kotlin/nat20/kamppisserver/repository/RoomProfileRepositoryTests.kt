package nat20.kamppisserver.repository

import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.RoomPreference
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.UserStatus
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
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roomProfileRepository: RoomProfileRepository,
){
    @Test
    fun `should return RoomProfiles whose rent is below or at user's max rent criteria`() {
        val numberOfMatchingRoomProfiles: Int = 18
        val user: User? = userRepository.findByIdAndStatus(27L, UserStatus.ACTIVE)
        val roomPreference: RoomPreference = roomPreferenceRepository.findByUserIdAndStatus(user!!.id!!, UserStatus.ACTIVE)
            ?: fail("Expected RoomPreference but found null")

        // Set other criteria to values that do not affect the max rent filtering
        val hasPrivateRoom = null;
        val maxRoommates = 100
        val preferredLocations = mutableListOf("HELSINKI", "VANTAA", "ESPOO")

        val listOfRoomProfiles: MutableIterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                user.id!!,
                roomPreference.maxRent,
                hasPrivateRoom,
                maxRoommates,
                preferredLocations
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }

    @Test
    fun `should return RoomProfiles that match user's room privacy criteria`() {
        val numberOfMatchingRoomProfiles: Int = 14
        val user: User? = userRepository.findByIdAndStatus(27L, UserStatus.ACTIVE)
        val roomPreference: RoomPreference = roomPreferenceRepository.findByUserIdAndStatus(user!!.id!!, UserStatus.ACTIVE)
            ?: fail("Expected RoomPreference but found null")

        // Set other criteria to values that do not affect the room privacy filtering
        val maxRent = 10000
        val maxRoommates = 100
        val preferredLocations = mutableListOf("HELSINKI", "VANTAA", "ESPOO")

        val listOfRoomProfiles: MutableIterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                user.id!!,
                maxRent,
                roomPreference.hasPrivateRoom,
                maxRoommates,
                preferredLocations
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }

    @Test
    fun `should return RoomProfiles for flats whose total roommate count is below or at user's max roommate criteria`() {
        val numberOfMatchingRoomProfiles: Int = 14
        val user: User? = userRepository.findByIdAndStatus(27L, UserStatus.ACTIVE)
        val roomPreference: RoomPreference = roomPreferenceRepository.findByUserIdAndStatus(user!!.id!!, UserStatus.ACTIVE)
            ?: fail("Expected RoomPreference but found null")

        // Set other criteria to values that do not affect the max roommate filtering
        val maxRent = 10000
        val hasPrivateRoom = null
        val preferredLocations = mutableListOf("HELSINKI", "VANTAA", "ESPOO")

        val listOfRoomProfiles: MutableIterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                user.id!!,
                maxRent,
                hasPrivateRoom,
                roomPreference.maxRoommates,
                preferredLocations
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }



    @Test
    fun `should return RoomProfiles for flats whose location is in user's location preferences`() {
        val numberOfMatchingRoomProfiles: Int = 18
        val user: User? = userRepository.findByIdAndStatus(27L, UserStatus.ACTIVE)
        val roomPreference: RoomPreference = roomPreferenceRepository.findByUserIdAndStatus(user!!.id!!, UserStatus.ACTIVE)
            ?: fail("Expected RoomPreference but found null")

        // Set other criteria to values that do not affect the location filtering
        val maxRent = 10000
        val hasPrivateRoom = null
        val maxRoommates = 100

        val listOfRoomProfiles: MutableIterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                user.id!!,
                maxRent,
                hasPrivateRoom,
                maxRoommates,
                roomPreference.locationPreferences!!.map {it.name }.toMutableList()
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }

    @Test
    fun `should return the correct amount of room profiles when all criteria are used`() {
        val numberOfMatchingRoomProfiles: Int = 10
        val user: User? = userRepository.findByIdAndStatus(27L, UserStatus.ACTIVE)
        val roomPreference: RoomPreference = roomPreferenceRepository.findByUserIdAndStatus(user!!.id!!, UserStatus.ACTIVE)
            ?: fail("Expected RoomPreference but found null")

        val listOfRoomProfiles: MutableIterable<RoomProfile> =
            roomProfileRepository.findRoomProfilesThatMeetCriteria(
                user.id!!,
                roomPreference.maxRent,
                roomPreference.hasPrivateRoom,
                roomPreference.maxRoommates,
                roomPreference.locationPreferences!!.map {it.name}.toMutableList()
            )

        assertEquals(numberOfMatchingRoomProfiles, listOfRoomProfiles.count())
    }
}