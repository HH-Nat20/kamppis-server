package nat20.kamppisserver.service

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
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

/**
 * Test class for UserService.
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

    @BeforeEach
    fun setup() {
        testUser = userRepository.save(User(
            email = "john.doe@example.com",
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = LocalDate.of(1990, 5, 14),
            gender = Gender.MALE
        ))

        testUserProfile = userProfileRepository.save(UserProfile(
            user = testUser,
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = LocalDate.of(1990, 5, 14),
            gender = Gender.MALE,
            maxRent = MaxRent.LOW,
            cleanliness = Cleanliness.SPOTLESS
        ))
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

        val restoredUser = userService.restore(testUser, testUser.id!!)

        assertEquals(UserStatus.ACTIVE, restoredUser.status)
        assertNull(restoredUser.deletedAt)

        val restoredUserProfile = userProfileRepository.findByIdActive(testUser.id!!)
        assertNull(restoredUserProfile?.deletedAt)
    }
}