package nat20.kamppisserver.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.*
import nat20.kamppisserver.domain.getUsersFromProfile
import nat20.kamppisserver.repository.SwipeRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SwipeServiceTests {
    private lateinit var swipeRepository: SwipeRepository
    private lateinit var matchService: MatchService
    private lateinit var swipeService: SwipeService

    val user1 = User(
        id = 1L,
        email = "alice.smith@test.com",
        firstName = "Alice",
        lastName = "Smith",
        dateOfBirth = LocalDate.of(1990, 5, 14),
        gender = Gender.FEMALE,
        lookingFor = LookingFor.OTHER_USER_PROFILES
    )

    val profile1 = UserProfile(
        user = user1,
        cleanliness = Cleanliness.TIDY,
        lifestyle = mutableSetOf(Lifestyle.STUDENT),
        pets = Pets.OK_WITH_PETS
    )
    val user2 = User(
        id = 2L,
        email = "bob.smith@test.com",
        firstName = "Bob",
        lastName = "Smith",
        dateOfBirth = LocalDate.of(1990, 5, 14),
        gender = Gender.MALE,
        lookingFor = LookingFor.OTHER_USER_PROFILES
    )

    val profile2 = UserProfile(
        user = user2,
        cleanliness = Cleanliness.TIDY,
        lifestyle = mutableSetOf(Lifestyle.STUDENT),
        pets = Pets.OK_WITH_PETS
    )

    @BeforeEach
    fun setup() {
        swipeRepository = mockk(relaxed = true)
        matchService = mockk(relaxed = true)
        swipeService = SwipeService(matchService, swipeRepository)

        // Mock the save method to return the Swipe object itself with id = 1
        every { swipeRepository.save(any()) } answers {
            (firstArg<Swipe>()).apply { id = 1L } }
    }

    @Test
    fun `single right swipe should not create a match`() {
        // Given: Profile 1 has not swiped right on Profile 2
        every {
            swipeRepository.existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(profile1, profile2, true)
        } returns false

        // When: Profile 1 swipes right on Profile 2
        swipeService.swipe(profile1, profile2, true)

        // Then
        verify(exactly = 0) { matchService.createMatch(users = getUsersFromProfile(profile1) + getUsersFromProfile(profile2)) }
    }

    @Test
    fun `mutual right swipes should create a match`() {
        // Given: Profile 2 has already swiped right on Profile 1
        every { swipeRepository.existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(profile2, profile1, true)
        } returns true

        // When: Profile 1 now swipes right on Profile 2
        swipeService.swipe(profile1, profile2, true)

        // Then
        verify(exactly = 1) { matchService.createMatch(users = getUsersFromProfile(profile1) + getUsersFromProfile(profile2))  }
    }

    @Test
    fun `left swipe should never create a match`() {
        // Given: Profile 2 has already swiped right on Profile 1
        every { swipeRepository.existsBySwipingProfileAndSwipedProfileAndIsRightSwipe(profile2, profile1, true)
        } returns true

        // When: Profile 1 swipes left on Profile 2
        swipeService.swipe(profile1, profile2, false)

        // Then
        verify(exactly = 0) { matchService.createMatch(users = getUsersFromProfile(profile1) + getUsersFromProfile(profile2)) }
    }


}