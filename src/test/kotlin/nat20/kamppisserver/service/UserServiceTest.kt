package nat20.kamppisserver.service

import exception.DuplicateEmailException
import exception.EntityNotFoundException
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserRequest
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

/**
 * Test class for UserService.
 * TODO: Test get preferences, create and update
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserServiceTest @Autowired constructor(
    val userService: UserService,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository
) {

    lateinit var testUser: User
    lateinit var testUserProfile: UserProfile
    lateinit var testUserRequest: UserRequest

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

        testUserProfile = userProfileRepository.save(UserProfile(
            user = testUser,
            cleanliness = Cleanliness.SPOTLESS,
            lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD),
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
    fun `should throw EntityNotFoundException when user does not exist`() {
        val userId = 999L

        assertThrows<EntityNotFoundException> {
            userService.update(testUserRequest, userId)
        }
    }

    @Test
    fun `delete should mark user as inactive and set deletedAt`() {
        userService.delete(testUser.id!!)

        val deletedUser = userRepository.findById(testUser.id!!).get()
        val deletedUserProfile = userProfileRepository.findByUserIdAndStatus(testUser.id!!, UserStatus.INACTIVE)

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
}