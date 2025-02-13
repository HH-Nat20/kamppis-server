package nat20.kamppisserver.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.Gender
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
// TODO: import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

/**
 * Test class for UserProfileService. Tests that edit and delete methods return
 * values accordingly, and throw an exception in case of invalid id:s.
*/
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// TODO: @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserProfileServiceTest {

    @Autowired
    private lateinit var repository: UserProfileRepository

    @Autowired
    private lateinit var service: UserProfileService

    @Test
    fun `update should modify and save user profile`() {

        val updatedProfile = repository.findByIdOrNull(1L)

        val result = updatedProfile?.let { service.update(it, 1L) }

        if (result != null) {
            assertNotNull(result.updatedAt)
        }
    }

    @Test
    fun `update should throw EntityNotFoundException when profile not found`() {
        val id = 6L
        val updatedProfile = UserProfile(
            id = id,
            user = User(email = "test@example.com"),
            firstName = "Test",
            lastName = "User",
            dateOfBirth = LocalDate.of(1995, 1, 1),
            gender = Gender.NOT_IMPORTANT
        )

        val exception = assertThrows<EntityNotFoundException> {
            service.update(updatedProfile, id)
        }

        assertEquals("User profile with id $id not found", exception.message)
    }

    @Test
    fun `delete should mark profile as deleted`() {
        val deletedProfile = repository.findByIdOrNull(1L)

        if (deletedProfile != null) {
            deletedProfile.id?.let { service.delete(it) }
        }

        if (deletedProfile != null) {
            assertNotNull(deletedProfile.deletedAt)
        }
    }

    @Test
    fun `delete should throw EntityNotFoundException when profile not found`() {
        val id = 6L

        val exception = assertThrows<EntityNotFoundException> {
            service.delete(id)
        }

        assertEquals("User profile with id $id not found", exception.message)
    }
}