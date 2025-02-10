package nat20.kamppisserver.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.SwipeRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SwipeServiceTests {
    private lateinit var swipeRepository: SwipeRepository
    private lateinit var matchService: MatchService
    private lateinit var swipeService: SwipeService

    private val userA = User(email = "a@example.com")
    private val userB = User(email = "b@example.com")

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
        // Given: User B has not swiped right on User A
        every {
            swipeRepository.existsBySwipingUserAndSwipedUserAndIsRightSwipe(userB, userA, true)
        } returns false

        // When: User A swipes right on User B
        swipeService.swipe(userA, userB, true)

        // Then
        verify(exactly = 0) { matchService.createMatch(any(), any()) }
    }

    @Test
    fun `mutual right swipes should create a match`() {
        // Given: User B has already swiped right on User A
        every { swipeRepository.existsBySwipingUserAndSwipedUserAndIsRightSwipe(userB, userA, true)
        } returns true

        // When: User A now swipes right on User B
        swipeService.swipe(userA, userB, true)

        // Then
        verify(exactly = 1) { matchService.createMatch(userA, userB)  }
    }

    @Test
    fun `left swipe should never create a match`() {
        // When: User A swipes left on User B
        swipeService.swipe(userA, userB, false)

        // Then
        verify(exactly = 0) { matchService.createMatch(any(), any()) }
    }


}