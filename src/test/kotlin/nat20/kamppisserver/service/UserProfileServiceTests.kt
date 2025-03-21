package nat20.kamppisserver.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.domain.enums.Cleanliness
import nat20.kamppisserver.repository.UserProfileRepository
import org.junit.jupiter.api.BeforeEach
import io.mockk.*
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.Lifestyle
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
            gender = Gender.FEMALE
        )

        val existingProfile = UserProfile(
            user = user,
            cleanliness = Cleanliness.TIDY,
            lifestyle = mutableSetOf(Lifestyle.STUDENT)
        )

        val updateDTO = UserProfileDTO(
            userId = user.id!!,
            bio = "New bio",
            cleanliness = Cleanliness.MESSY,
            lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL)
        )

        every { userProfileRepository.findByIdActive(123L) } returns existingProfile
        every { userProfileRepository.save(any()) } answers { firstArg() }

        val result = service.update(updateDTO, 123L)

        assertEquals(updateDTO.bio, result.bio)
        assertEquals(updateDTO.cleanliness, result.cleanliness)
        assertEquals(updateDTO.lifestyle, result.lifestyle)
        assertEquals(updateDTO.photos, result.photos)

        verify(exactly = 1) { userProfileRepository.findByIdActive(123L) }
        verify(exactly = 1) { userProfileRepository.save(existingProfile) }
    }

    @Test
    fun `update() should throw EntityNotFoundException when profile does not exist`() {
        every { userProfileRepository.findByIdActive(999L) } returns null

        val updateDTO = UserProfileDTO(userId = 1L, bio = "New bio", id = 999L)

        val exception = assertThrows<EntityNotFoundException> {
            service.update(updateDTO, 999L)
        }

        assertEquals("User profile with id 999 not found", exception.message)
        verify(exactly = 1) { userProfileRepository.findByIdActive(999L) }
        verify(exactly = 0) { userProfileRepository.save(any()) }
    }
}