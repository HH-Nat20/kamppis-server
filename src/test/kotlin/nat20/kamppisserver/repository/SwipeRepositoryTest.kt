package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.setup.ContextSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SwipeRepositoryTest @Autowired constructor(
    val swipeRepository: SwipeRepository,
    val userProfileRepository: UserProfileRepository,
    val userRepository: UserRepository,
    val flatRepository: FlatRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roomProfileRepository: RoomProfileRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository
) {

    lateinit var user1: User
    lateinit var user2: User
    lateinit var swipingProfile: UserProfile
    lateinit var swipedProfile: UserProfile
    lateinit var contextSetup: ContextSetup

    @BeforeEach
    fun init() {
        contextSetup = ContextSetup(
            userRepository,
            userProfileRepository,
            flatRepository,
            roomProfileRepository,
            roomPreferenceRepository,
            roommatePreferenceRepository
        )
        contextSetup.setup()
        user1 = contextSetup.user
        user2 = contextSetup.swipingUser
        swipingProfile = contextSetup.userProfile
        swipedProfile = contextSetup.swipingUserProfile
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