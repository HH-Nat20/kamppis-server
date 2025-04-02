package nat20.kamppisserver.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.repository.UserProfileRepository
import org.junit.jupiter.api.BeforeEach
import io.mockk.*
import nat20.kamppisserver.domain.UserProfileRequest
import nat20.kamppisserver.domain.enums.*
import nat20.kamppisserver.repository.UserRepository
import java.time.LocalDate

/**
 * Test class for UserProfileService. Tests that edit and delete methods return
 * values accordingly, and throw an exception in case of invalid id:s.
*/
class UserProfileServiceTests {
    private lateinit var userRepository: UserRepository
    private lateinit var userProfileRepository: UserProfileRepository
    private lateinit var service: UserProfileService

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        userProfileRepository = mockk()
        service = UserProfileService(userRepository, userProfileRepository)
    }

    @Test
    fun `update() should update and return UserProfileDTO when profile exists`() {
        val user = User(
            id = 999L,
            email = "alice.smith@test.com",
            firstName = "Alice",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1990, 5, 14), // Age 34
            gender = Gender.FEMALE,
            lookingFor = LookingFor.OTHER_USER_PROFILES
        )

        val existingProfile = UserProfile(
            user = user,
            cleanliness = Cleanliness.TIDY,
            lifestyle = mutableSetOf(Lifestyle.STUDENT),
            pets = Pets.OK_WITH_PETS
        )

        val updateRequest = UserProfileRequest(
            userId = user.id!!,
            bio = "New bio",
            cleanliness = Cleanliness.MESSY,
            lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL),
            pets = Pets.PET_OWNER
        )

        every { userRepository.findByIdAndStatus(999L, UserStatus.ACTIVE) } returns user
        every { userProfileRepository.findByIdActive(123L) } returns existingProfile
        every { userProfileRepository.save(any()) } answers { firstArg() }

        val result = service.update(updateRequest, 123L)

        assertEquals(updateRequest.bio, result.bio)
        assertEquals(updateRequest.cleanliness, result.cleanliness)
        assertEquals(updateRequest.lifestyle, result.lifestyle)
        assertEquals(updateRequest.photos, result.photos)

        verify(exactly = 1) { userProfileRepository.findByIdActive(123L) }
        verify(exactly = 1) { userProfileRepository.save(existingProfile) }
    }

    @Test
    fun `update() should throw EntityNotFoundException when profile does not exist`() {
        every { userProfileRepository.findByIdActive(999L) } returns null

        val updateRequest = UserProfileRequest(userId = 1L, bio = "New bio", id = 999L)

        val exception = assertThrows<EntityNotFoundException> {
            service.update(updateRequest, 999L)
        }

        assertEquals("User profile with id 999 not found", exception.message)
        verify(exactly = 1) { userProfileRepository.findByIdActive(999L) }
        verify(exactly = 0) { userProfileRepository.save(any()) }
    }
}