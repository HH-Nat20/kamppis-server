package nat20.kamppisserver.service

import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.Gender
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository
import org.junit.jupiter.api.extension.ExtendWith
// TODO: import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

/**
 * Test class for UserProfileService. Tests that edit and delete methods return
 * values accordingly, and throw an exception in case of invalid id:s.
*/
@ExtendWith(MockKExtension::class)
// TODO: @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserProfileServiceTest {

    private val repository: UserProfileRepository = mockk()
    private val service = UserProfileService(repository)

    @Test
    fun `update should modify and save user profile`() {

        val existingProfile = UserProfile(
            user = User(email = "alice.smith@example.com"),
            firstName = "Alice",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1970, 1, 1),
            gender = Gender.FEMALE,
            id = 1L)

        val updatedProfile = UserProfile(
            user = User(email = "bob.johnson@example.com"),
            firstName = "Bob",
            lastName = "Johnson",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            gender = Gender.MALE)

        every { repository.findByIdOrNull(1L) } returns existingProfile
        every { repository.save(any()) } answers { firstArg() }

        val result = service.update(updatedProfile, 1L)

        assertNotNull(result.updatedAt)
    }

    @Test
    fun `update should throw EntityNotFoundException when profile not found`() {
        val id = 1L
        val updatedProfile = UserProfile(
            user = User(email = "bob.johnson@example.com"),
            firstName = "Bob",
            lastName = "Johnson",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            gender = Gender.MALE,
            id = 2L)

        every { repository.findByIdOrNull(id) } returns null

        val exception = assertThrows<EntityNotFoundException> {
            service.update(updatedProfile, id)
        }

        assertEquals("User profile with id $id not found", exception.message)
    }

    @Test
    fun `delete should mark profile as deleted`() {
        val id = 1L
        val existingProfile = UserProfile(
            user = User(email = "alice.smith@example.com"),
            firstName = "Alice",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1970, 1, 1),
            gender = Gender.FEMALE,
            id = 1L)

        every { repository.findByIdOrNull(id) } returns existingProfile
        every { repository.save(any()) } answers { firstArg() }

        service.delete(id)

        assertNotNull(existingProfile.deletedAt)
    }

    @Test
    fun `delete should throw EntityNotFoundException when profile not found`() {
        val id = 1L

        every { repository.findByIdOrNull(id) } returns null

        val exception = assertThrows<EntityNotFoundException> {
            service.delete(id)
        }

        assertEquals("User profile with id $id not found", exception.message)
    }
}