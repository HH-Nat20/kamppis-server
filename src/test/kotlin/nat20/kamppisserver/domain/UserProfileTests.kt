package nat20.kamppisserver.domain

import nat20.kamppisserver.domain.enums.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test
import nat20.kamppisserver.repository.UserProfileRepository
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import kotlin.test.assertEquals


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserProfileTests @Autowired constructor(
    val userProfileRepository: UserProfileRepository
) {

    @Test
    fun `toDTO() should correctly convert UserProfile to UserProfileDTO`() {
        val user = User(
            email = "alice.smith@example.com",
            firstName = "Alice",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1990, 5, 14), // Age 34
            gender = Gender.FEMALE
        )

        val profile = UserProfile(
            user = user,
            cleanliness = Cleanliness.TIDY,
            lifestyle = mutableSetOf(Lifestyle.STUDENT)
        )

        val dto = profile.toDTO()

        assertEquals(user.id, dto.userId)
        assertEquals(profile.cleanliness, dto.cleanliness)
        assertEquals(profile.lifestyle, dto.lifestyle)
        assertEquals(profile.id, dto.id)
    }
}