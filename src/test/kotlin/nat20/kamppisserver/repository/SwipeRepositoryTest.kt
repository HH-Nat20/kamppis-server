package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.Gender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SwipeRepositoryTest @Autowired constructor(
    val swipeRepository: SwipeRepository,
    val userProfileRepository: UserProfileRepository,
    val userRepository: UserRepository
) {

    lateinit var user1: User
    lateinit var user2: User
    lateinit var swipingProfile: UserProfile
    lateinit var swipedProfile: UserProfile

    @BeforeEach
    fun setup() {
        user1 = userRepository.save(
            User(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                dateOfBirth = LocalDate.of(1980, 1, 1),
                gender = Gender.MALE
            )
        )

        user2 = userRepository.save(
            User(
                firstName = "Jane",
                lastName = "Doe",
                email = "jane.doe@example.com",
                dateOfBirth = LocalDate.of(1990, 1, 1),
                gender = Gender.FEMALE
            )
        )

        swipingProfile = userProfileRepository.save(
            UserProfile(
                user = user1
            )
        )

        swipedProfile = userProfileRepository.save(
            UserProfile(
                user = user2
            )
        )
    }

    @Test
    fun `existsBySwipingProfileAndSwipedProfileAndIsRightSwipe returns true when swipe exists`() {
        val swipe = Swipe(
            swipingProfile = swipingProfile,
            swipedProfile = swipedProfile,
            isRightSwipe = true
        )
        swipeRepository.save(swipe)

        val exists = swipeRepository.existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(
            swipingProfile,
            swipedProfile,
            true
        )

        assertThat(exists).isTrue()
    }

    @Test
    fun `existsBySwipingProfileAndSwipedProfileAndIsRightSwipe returns false when no such swipe exists`() {
        val exists = swipeRepository.existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(
            swipingProfile,
            swipedProfile,
            true
        )

        assertThat(exists).isFalse()
    }
}