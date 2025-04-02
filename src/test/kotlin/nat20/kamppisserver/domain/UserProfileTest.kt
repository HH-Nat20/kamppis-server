package nat20.kamppisserver.domain

import nat20.kamppisserver.domain.enums.*
import kotlin.test.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class UserProfileTest {

    @Test
    fun `toDTO() should correctly convert UserProfile to UserProfileDTO`() {
        val user = User(
            id = 999L,
            email = "alice.smith@test.com",
            firstName = "Alice",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1990, 5, 14),
            gender = Gender.FEMALE,
            lookingFor = LookingFor.OTHER_USER_PROFILES
        )

        val profile = UserProfile(
            user = user,
            cleanliness = Cleanliness.TIDY,
            lifestyle = mutableSetOf(Lifestyle.STUDENT),
            pets = Pets.OK_WITH_PETS
        )

        val dto = profile.toDTO()

        assertEquals(user.id, dto.userId)
        assertEquals(profile.cleanliness, dto.cleanliness)
        assertEquals(profile.lifestyle, dto.lifestyle)
        assertEquals(profile.id, dto.id)
    }
}