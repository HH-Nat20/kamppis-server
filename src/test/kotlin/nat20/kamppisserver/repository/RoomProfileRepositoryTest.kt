package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.ProfileStatus
import nat20.kamppisserver.setup.ContextSetup
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomProfileRepositoryTest @Autowired constructor(
    val roomProfileRepository: RoomProfileRepository,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository,
    val flatRepository: FlatRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository,
    val testEntityManager: TestEntityManager
) {

    lateinit var user: User
    lateinit var userProfile: UserProfile
    lateinit var flat: Flat
    lateinit var roomProfile: RoomProfile
    lateinit var deletedRoomProfile: RoomProfile
    lateinit var roomPreference: RoomPreference
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
        user = contextSetup.user
        userProfile = contextSetup.userProfile
        flat = contextSetup.flat
        roomProfile = contextSetup.roomProfile
        deletedRoomProfile = contextSetup.deletedRoomProfile
        roomPreference  = contextSetup.roomPreference
    }

    @Test
    fun `findAllActive should return only non-deleted room profiles`() {
        val result = roomProfileRepository.findAllActive()

        assertEquals(2, result.size)
        assertEquals(ProfileStatus.ACTIVE, result[0].status)
        assertNull(result[0].deletedAt)
    }

    @Test
    fun `findByIdActive should return room profile if not deleted`() {
        val found = roomProfileRepository.findByIdActive(roomProfile.id!!)
        assertNotNull(found)
        assertEquals(ProfileStatus.ACTIVE, found?.status)
    }

    @Test
    fun `findByUserIdAndStatus returns profiles for user and status`() {
        val result = roomProfileRepository.findByUserIdAndStatus(user.id!!, ProfileStatus.ACTIVE)
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `findUsersRoomProfiles returns profile for specific user and profile`() {
        val result = roomProfileRepository.findUsersRoomProfiles(roomProfile.id!!, user.id!!)
        assertNotNull(result)
        assertEquals(1, result?.size)
    }

    @Test
    fun `findRoomProfilesThatMeetCriteria filters correctly`() {
        val result = roomProfileRepository.findRoomProfilesThatMeetCriteria(
            pageable = PageRequest.of(0, 10),
            userProfileId = userProfile.id!!,
            maxRent = 1000,
            hasPrivateRoom = true,
            maxRoommates = 3,
            locationPreferences = roomPreference.locationPreferences!!.map {it.name }.toMutableList()
        )
        assertEquals(1, result.totalElements)
    }

    @Test
    fun `findIfFlatIsPetHousehold returns true if any user has PET_OWNER`() {
        testEntityManager.entityManager.createNativeQuery("INSERT INTO room_profiles_users (room_profile_id, user_id) VALUES (?, ?)")
            .setParameter(1, roomProfile.id)
            .setParameter(2, user.id)
            .executeUpdate()

        val result = roomProfileRepository.findIfFlatIsPetHousehold(roomProfile.id)
        assertTrue(result)
    }

    @Test
    fun `findRoomProfileByUserProfileId returns correct profiles`() {
        testEntityManager.entityManager.createNativeQuery("INSERT INTO room_profiles_users (room_profile_id, user_id) VALUES (?, ?)")
            .setParameter(1, roomProfile.id)
            .setParameter(2, user.id)
            .executeUpdate()

        val result = roomProfileRepository.findRoomProfileByUserProfileId(userProfile.id)
        assertFalse(result.isEmpty())
    }
}