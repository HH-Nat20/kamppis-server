package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.Gender
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfilePhotoRepositoryTest @Autowired constructor(
    val profilePhotoRepository: ProfilePhotoRepository,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository
) {

    @Test
    fun `findByProfileId returns photos associated with profile`() {
        val user = userRepository.save(
            User(
                firstName = "Test",
                lastName = "User",
                email = "test.user@example.com",
                dateOfBirth = LocalDate.of(1990, 1, 1),
                gender = Gender.OTHER
            )
        )

        val userProfile = userProfileRepository.save(UserProfile(user = user))

        val photo1 = ProfilePhoto(
            profile = userProfile,
            url = "http://example.com/1.jpg",
            isProfilePhoto = true
        )

        val photo2 = ProfilePhoto(
            profile = userProfile,
            url = "http://example.com/2.jpg",
            isProfilePhoto = false
        )

        profilePhotoRepository.saveAll(listOf(photo1, photo2))

        val foundPhotos = profilePhotoRepository.findByProfileId(userProfile.id!!)
        assertThat(foundPhotos).hasSize(2)
        assertThat(foundPhotos.map { it.url }).containsExactlyInAnyOrder("http://example.com/1.jpg", "http://example.com/2.jpg")
    }
}