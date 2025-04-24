package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.Pets
import nat20.kamppisserver.domain.enums.ProfileStatus
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomProfileRepositoryTest @Autowired constructor(
    val roomProfileRepository: RoomProfileRepository,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository,
    val flatRepository: FlatRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val testEntityManager: TestEntityManager
) {

    lateinit var user: User
    lateinit var userProfile: UserProfile
    lateinit var flat: Flat
    lateinit var roomProfile: RoomProfile
    lateinit var deletedRoomProfile: RoomProfile
    lateinit var roomPreference: RoomPreference

    @BeforeEach
    fun setup() {
        user = userRepository.save(
            User(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                dateOfBirth = LocalDate.of(1980, 1, 1),
                gender = Gender.MALE
            )
        )

        userProfile = userProfileRepository.save(
            UserProfile(
                user = user,
                pets = Pets.PET_OWNER
            )
        )

        flat = flatRepository.save(
            Flat(
                name = "Nice place",
                description = "Sunny",
                location = City.HELSINKI,
                totalRoommates = 2,
                petHousehold = true
            )
        )

        roomProfile = roomProfileRepository.save(
            RoomProfile(
                users = mutableListOf(user),
                flat = flat,
                rent = 800,
                isPrivateRoom = true,
                furnished = false,
                bio = "A cool place"
            )
        )

        deletedRoomProfile = RoomProfile(
            users = mutableListOf(user),
            flat = flat,
            rent = 800,
            isPrivateRoom = true,
            furnished = false,
            bio = "A deleted place"
        )

        deletedRoomProfile.status = ProfileStatus.INACTIVE
        deletedRoomProfile.deletedAt = LocalDateTime.now()
        roomProfileRepository.save(deletedRoomProfile)

        roomPreference = roomPreferenceRepository.save(
            RoomPreference(
                user = user,
                maxRent = 750,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            )
        )
    }

    @Test
    fun `findAllActive should return only non-deleted room profiles`() {
        val result = roomProfileRepository.findAllActive()

        assertEquals(1, result.size)
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
            userProfileId = 1L,
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