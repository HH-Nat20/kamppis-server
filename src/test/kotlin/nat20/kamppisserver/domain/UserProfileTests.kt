package nat20.kamppisserver.domain

import nat20.kamppisserver.TestDatabaseMockDataConfiguration
import nat20.kamppisserver.domain.enums.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

import nat20.kamppisserver.repository.UserProfileRepository
import org.junit.jupiter.api.TestInstance
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals


@DataJpaTest
@Import(TestDatabaseMockDataConfiguration::class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserProfileTests @Autowired constructor(
    val userProfileRepository: UserProfileRepository
) {

    @Test
    fun `toUserProfileDTO should return a valid DTO data class` () {
        val testUserProfileDTO: UserProfileDTO = UserProfileDTO(
            //userId = 1,
            firstName = "Alice",
            lastName = "Smith",
            age = 34,
            gender = Gender.FEMALE,
            userPhotos = userProfileRepository.findByIdOrNull(1L)?.userPhotos?.map { toUserPhotoDTO(it) }?.toMutableList(),
            preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO),
            maxRent = MaxRent.LOW,
            cleanliness = Cleanliness.SPOTLESS,
            lifestyle = mutableListOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
            bio = "I'm a passionate traveler who loves exploring new cultures and cuisines. When I'm not studying, you can find me hiking in nature or experimenting with new recipes in the kitchen.",
            id = 1
        )

        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!

        assertEquals(testUserProfileDTO, toUserProfileDTO(userProfile))
    }
}