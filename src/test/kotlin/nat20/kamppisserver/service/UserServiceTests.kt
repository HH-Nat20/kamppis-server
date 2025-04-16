package nat20.kamppisserver.service

import exception.DuplicateEmailException
import exception.EntityNotFoundException
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.*
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

/**
 * Test class for UserService.
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserServiceTests @Autowired constructor(
    val userService: UserService,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository
) {

    lateinit var testUser: User
    lateinit var deletedTestUser: User
    lateinit var testUserProfile: UserProfile
    lateinit var testUserRequest: UserRequest
    lateinit var testUserPreferenceRequest: UserPreferenceRequest

    @BeforeEach
    fun setup() {
        testUser = userRepository.save(User(
            email = "john.doe@example.com",
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = LocalDate.of(1990, 5, 14),
            gender = Gender.MALE,
            lookingFor = LookingFor.OTHER_USER_PROFILES
        ))

        deletedTestUser = userRepository.save(User(
            email = "deleted.user@example.com",
            firstName = "Deleted",
            lastName = "User",
            dateOfBirth = LocalDate.of(1990, 5, 14),
            gender = Gender.MALE,
            lookingFor = LookingFor.OTHER_USER_PROFILES,
            status = UserStatus.INACTIVE,
            deletedAt = LocalDateTime.now().minusDays(31)
        ))

        testUserProfile = userProfileRepository.save(UserProfile(
            user = testUser,
            cleanliness = Cleanliness.SPOTLESS,
            lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD),
            pets = Pets.OK_WITH_PETS,
            bio = "Test bio"
        ))

        testUserRequest = UserRequest(
            firstName = "Jane",
            lastName = "Doe",
            email = "jane.doe@example.com",
            dateOfBirth = LocalDate.of(1999, 1, 1),
            gender = Gender.FEMALE,
            lookingFor = LookingFor.ROOM_PROFILES
        )

        testUserPreferenceRequest = UserPreferenceRequest(
            roomPreference = RoomPreferenceDTO(
                maxRent = 1200,
                hasPrivateRoom = true,
                maxRoommates = 1,
                locationPreferences = mutableListOf(City.HELSINKI)
            ),
            roommatePreference = RoommatePreferenceDTO(
                minAgePreference = 22,
                maxAgePreference = 30,
                genderPreferences = mutableListOf(Gender.MALE),
                locationPreferences = mutableListOf(City.HELSINKI)
            )
        )
    }

    @Test
    fun `add should create a user successfully`() {
        assertNull(userRepository.findByEmail(testUserRequest.email))

        val result = userService.add(testUserRequest)

        assertNotNull(result)
        assertEquals(testUserRequest.email, result.email)
    }

    @Test
    fun `add should throw DuplicateEmailException when email already exists`() {
        val request = testUser.toUserRequest()

        assertThrows<DuplicateEmailException> {
            userService.add(request)
        }
    }

    @Test
    fun `should update user successfully`() {
        val result = userService.update(testUserRequest, testUser.id!!)

        assertEquals(testUserRequest.firstName, result.firstName)
        assertEquals(testUserRequest.lastName, result.lastName)
        assertEquals(testUserRequest.email, result.email)
        assertEquals(testUserRequest.dateOfBirth, result.dateOfBirth)
        assertEquals(testUserRequest.gender, result.gender)
        assertEquals(testUserRequest.lookingFor, result.lookingFor)
        assertNotNull(testUser.updatedAt)
    }

    @Test
    fun `should update user preferences successfully`() {
        val result = userService.updatePreferences(testUserPreferenceRequest, testUser.id!!)

        assertEquals(testUserPreferenceRequest.roomPreference?.maxRent, result.roomPreference?.maxRent)
        assertEquals(testUserPreferenceRequest.roomPreference?.hasPrivateRoom, result.roomPreference?.hasPrivateRoom)
        assertEquals(testUserPreferenceRequest.roomPreference?.maxRoommates, result.roomPreference?.maxRoommates)
        assertEquals(testUserPreferenceRequest.roomPreference?.locationPreferences, result.roomPreference?.locationPreferences)

        assertEquals(testUserPreferenceRequest.roommatePreference?.minAgePreference, result.roommatePreference?.minAgePreference)
        assertEquals(testUserPreferenceRequest.roommatePreference?.maxAgePreference, result.roommatePreference?.maxAgePreference)
        assertEquals(testUserPreferenceRequest.roommatePreference?.genderPreferences, result.roommatePreference?.genderPreferences)
        assertEquals(testUserPreferenceRequest.roommatePreference?.locationPreferences, result.roommatePreference?.locationPreferences)

        assertNotNull(testUser.updatedAt)
    }

    @Test
    fun `update should throw EntityNotFoundException when user does not exist`() {
        val userId = 999L

        assertThrows<EntityNotFoundException> {
            userService.update(testUserRequest, userId)
        }

        assertThrows<EntityNotFoundException> {
            userService.updatePreferences(testUserPreferenceRequest, userId)
        }
    }

    @Test
    fun `delete should mark user as inactive and set deletedAt`() {
        userService.delete(testUser.id!!)

        val deletedUser = userRepository.findById(testUser.id!!).get()
        val deletedUserProfile = userProfileRepository.findByUserIdAndStatus(testUser.id!!, ProfileStatus.INACTIVE)

        assertEquals(UserStatus.INACTIVE, deletedUser.status)
        assertNotNull(deletedUser.deletedAt)
        assertNotNull(deletedUserProfile?.deletedAt)
    }

    @Test
    fun `restore should reactivate user and clear deletedAt`() {
        userService.delete(testUser.id!!)

        val restoredUser = userService.restore(testUser.id!!)

        assertEquals(UserStatus.ACTIVE, restoredUser.status)

        val restoredUserProfile = userProfileRepository.findByIdActive(testUser.id!!)
        assertNull(restoredUserProfile?.deletedAt)
    }

    @Test
    fun `should anonymize users deleted 30+ days ago`() {
        userService.permanentlyAnonymizeDeletedUsers()

        val anonymizedUser = userRepository.findById(deletedTestUser.id!!).get()

        assertEquals("****", anonymizedUser.firstName)
        assertEquals("****", anonymizedUser.lastName)
        assertEquals(LocalDate.of(2000, 1, 1), anonymizedUser.dateOfBirth)
        assertEquals(Gender.NOT_IMPORTANT, anonymizedUser.gender)
        assertEquals(LookingFor.OTHER_USER_PROFILES_OR_ROOM_PROFILES, anonymizedUser.lookingFor)
        assertEquals(UserStatus.DELETED, anonymizedUser.status)
        assertNotEquals("deleted.user@example.com", anonymizedUser.email)
    }
}