package nat20.kamppisserver.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import jakarta.persistence.EntityNotFoundException
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.MaxRent
import nat20.kamppisserver.domain.enums.Cleanliness
import nat20.kamppisserver.domain.toUserProfileDTO
import nat20.kamppisserver.repository.UserProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Test class for UserProfileService. Tests that edit and delete methods return
 * values accordingly, and throw an exception in case of invalid id:s.
*/
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserProfileServiceTest @Autowired constructor(
    val repository: UserProfileRepository,
    val service: UserProfileService
) {

    @Test
    fun `update should modify and save user profile`() {

        val userProfile = repository.findByIdActive(1L)
        val userProfileDTO = userProfile?.let { toUserProfileDTO(it) }
        if (userProfileDTO != null) {
            userProfile.id?.let { service.update(userProfileDTO, it) }
        }

        val updatedProfile = repository.findByIdActive(1L)

        assertNotNull(updatedProfile?.updatedAt)
    }

    @Test
    fun `update should throw EntityNotFoundException when profile not found`() {
        val id = 1000000L
        val updatedProfile = UserProfile(
            id = id,
            user = User(email = "test@example.com"),
            firstName = "Test",
            lastName = "User",
            dateOfBirth = LocalDate.of(1995, 1, 1),
            gender = Gender.NOT_IMPORTANT,
            maxRent = MaxRent.LOW,
            cleanliness = Cleanliness.TIDY,
        )

        val exception = assertThrows<EntityNotFoundException> {
            service.update(toUserProfileDTO(updatedProfile), id)
        }

        assertEquals("User profile with id $id not found", exception.message)
    }

}