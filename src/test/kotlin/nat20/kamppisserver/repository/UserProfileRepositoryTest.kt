package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.*

/**
 * Test class for UserProfileRepository.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserProfileRepositoryTest @Autowired constructor(
    val userProfileRepository: UserProfileRepository,
    val roomProfileRepository: RoomProfileRepository,
    val flatRepository: FlatRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository,
    val userRepository: UserRepository,
    val swipeRepository: SwipeRepository
) {

    val queryDate = LocalDate.of(2025, 2, 21)

    lateinit var user: User
    lateinit var swipingUser: User
    lateinit var deletedUser: User
    lateinit var userProfile: UserProfile
    lateinit var swipingProfile: UserProfile
    lateinit var deletedUserProfile: UserProfile
    lateinit var flat: Flat
    lateinit var roomProfile: RoomProfile
    lateinit var roommatePreference: RoommatePreference
    lateinit var swipingRoommatePreference: RoommatePreference

    @BeforeEach
    fun setup() {
        user = userRepository.save(
            User(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                dateOfBirth = LocalDate.of(1980, 1, 1),
                gender = Gender.MALE,
                lookingFor = LookingFor.OTHER_USER_PROFILES
            )
        )

        swipingUser = userRepository.save(
            User(
                firstName = "Jane",
                lastName = "Doe",
                email = "jane.doe@example.com",
                dateOfBirth = LocalDate.of(1990, 1, 1),
                gender = Gender.FEMALE,
                lookingFor = LookingFor.OTHER_USER_PROFILES
            )
        )

        deletedUser = User(
                firstName = "Deleted",
                lastName = "Doe",
                email = "deleted.doe@example.com",
                dateOfBirth = LocalDate.of(2000, 1, 1),
                gender = Gender.OTHER
            )

        deletedUser.status = UserStatus.INACTIVE
        deletedUser.deletedAt = LocalDateTime.now()
        userRepository.save(deletedUser)

        userProfile = userProfileRepository.save(
            UserProfile(
                user = user
            )
        )

        swipingProfile = userProfileRepository.save(
            UserProfile(
                user = swipingUser
            )
        )

        deletedUserProfile = UserProfile(
            user = deletedUser
        )

        deletedUserProfile.status = ProfileStatus.INACTIVE
        deletedUserProfile.deletedAt = LocalDateTime.now()
        userProfileRepository.save(deletedUserProfile)

        flat = flatRepository.save(
            Flat(
                name = "Nice place",
                description = "Sunny",
                location = City.HELSINKI,
                totalRoommates = 2
            )
        )

        roomProfile = roomProfileRepository.save(
            RoomProfile(
                users = mutableListOf(user),
                flat = flat,
                rent = 500,
                isPrivateRoom = true,
                furnished = false
            )
        )

        roommatePreference = roommatePreferenceRepository.save(
            RoommatePreference(
                user = user
            )
        )

        swipingRoommatePreference = roommatePreferenceRepository.save(
            RoommatePreference(
                user = swipingUser
            )
        )
    }

    @Test
    fun `findAllActive should return only non-deleted user profiles`() {
        val result = userProfileRepository.findAllActive()

        assertEquals(2, result.size)
        Assertions.assertEquals(ProfileStatus.ACTIVE, result[0].status)
        assertNull(result[0].deletedAt)
    }

    @Test
    fun `findByIdActive should return the correct user profile`() {
        val result = userProfileRepository.findByIdActive(userProfile.id!!)

        assertNotNull(result)
        assertEquals(userProfile.id, result.id)
        assertNull(result.deletedAt)
    }

    @Test
    fun `findByUserIdAndStatus should return the correct user profile based on userId and status`() {
        val result = userProfileRepository.findByUserIdAndStatus(user.id!!, ProfileStatus.ACTIVE)

        assertNotNull(result)
        assertEquals(user.id, result.user.id)
        assertEquals(ProfileStatus.ACTIVE, result.status)
    }

    @Test
    fun `should find user profiles without filters`() {
        val result = userProfileRepository.findUserProfilesThatMeetCriteria(
            pageable = PageRequest.of(0, 10),
            userProfileId = swipingProfile.id,
            queryDate = queryDate,
            minAgePreference = null,
            maxAgePreference = null,
            genderPreferences = null,
            locationPreferences = null
        )

        Assertions.assertEquals(1, result.totalElements)
    }

    @Test
    fun `should filter by age`() {
        roommatePreference.minAgePreference = 40
        roommatePreference.maxAgePreference = 50
        roommatePreferenceRepository.save(roommatePreference)

        val result = userProfileRepository.findUserProfilesThatMeetCriteria(
            pageable = PageRequest.of(0, 10),
            userProfileId = swipingProfile.id,
            queryDate = queryDate,
            minAgePreference = roommatePreference.minAgePreference,
            maxAgePreference = roommatePreference.maxAgePreference,
            genderPreferences = null,
            locationPreferences = null
        )

        Assertions.assertEquals(1, result.totalElements)
    }

    @Test
    fun `should filter by gender preferences`() {
        roommatePreference.genderPreferences = mutableListOf(Gender.MALE)
        roommatePreferenceRepository.save(roommatePreference)

        val result = userProfileRepository.findUserProfilesThatMeetCriteria(
            pageable = PageRequest.of(0, 10),
            userProfileId = swipingProfile.id,
            queryDate = queryDate,
            minAgePreference = null,
            maxAgePreference = null,
            genderPreferences = roommatePreference.genderPreferences?.map {it.name},
            locationPreferences = null
        )

        Assertions.assertEquals(1, result.totalElements)
    }

    @Test
    fun `should filter by location preferences`() {
        roommatePreference.locationPreferences = mutableListOf(City.HELSINKI)
        roommatePreferenceRepository.save(roommatePreference)

        val result = userProfileRepository.findUserProfilesThatMeetCriteria(
            pageable = PageRequest.of(0, 10),
            userProfileId = swipingProfile.id,
            queryDate = queryDate,
            minAgePreference = null,
            maxAgePreference = null,
            genderPreferences = null,
            locationPreferences = roommatePreference.locationPreferences?.map {it.name}
        )

        Assertions.assertEquals(1, result.totalElements)
    }

    @Test
    fun `should exclude profiles already swiped`() {
        swipeRepository.save(
            Swipe(
                swipingProfile = swipingProfile,
                swipedProfile = userProfile,
                isRightSwipe = true
            )
        )

        val result = userProfileRepository.findUserProfilesThatMeetCriteria(
            pageable = PageRequest.of(0, 10),
            userProfileId = swipingProfile.id,
            queryDate = queryDate,
            minAgePreference = null,
            maxAgePreference = null,
            genderPreferences = null,
            locationPreferences = null
        )

        Assertions.assertEquals(0, result.totalElements)
    }

    @Test
    fun `findUserProfilesWhoHaveSwipedRoomProfile should return users who swiped on the given room profile`() {
        val swipe = Swipe(
            swipingProfile = userProfile,
            swipedProfile = roomProfile,
            isRightSwipe = true
        )
        swipeRepository.save(swipe)

        val result = userProfileRepository.findUserProfilesWhoHaveSwipedRoomProfile(
            pageable = PageRequest.of(0, 10),
            roomProfileId = roomProfile.id!!
        )

        assertEquals(1, result.totalElements)
        assertEquals(user.id, result.content[0].user.id)
    }
}
