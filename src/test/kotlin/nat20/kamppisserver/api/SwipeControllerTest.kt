package nat20.kamppisserver.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import nat20.kamppisserver.security.SecurityConfig
import nat20.kamppisserver.domain.SwipeRequest
import nat20.kamppisserver.domain.SwipeResponse
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.*
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.service.SwipeService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@WebMvcTest(SwipeController::class)
@Import(SecurityConfig::class, JwtUtils::class) // Import your security config
class SwipeControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
){

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @MockkBean
    private lateinit var swipeService: SwipeService
    @MockkBean
    private lateinit var profileRepository: ProfileRepository

/*    Probably needed later
    @MockkBean
    private lateinit var authenticationManager: AuthenticationManager*/

    @Test
    fun `should create a swipe successfully`() {
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
            id = 1L,
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
            id = 2L,
            user = user2,
            cleanliness = Cleanliness.TIDY,
            lifestyle = mutableSetOf(Lifestyle.STUDENT),
            pets = Pets.OK_WITH_PETS
        )

        val swipeRequest = SwipeRequest(
            swipingProfileId = 1,
            swipedProfileId = 2,
            isRightSwipe = true
        )

        val swipeResponse = SwipeResponse(
            swipeId = 1,
            swipingProfile = profile1.toDTO(includeUserSummary = true),
            swipedProfile = profile2.toDTO(includeUserSummary = true),
            isRightSwipe = true,
            isMatch = false
        )

        every { profileRepository.findByIdAndStatus(1L, ProfileStatus.ACTIVE)} returns profile1
        every { profileRepository.findByIdAndStatus(2L, ProfileStatus.ACTIVE)} returns profile2
        every { swipeService.swipe(any(),any(), any()) } returns swipeResponse
        every { swipeService.principalInSwipingProfile(any(), any()) } returns true

        val jwt = jwtUtils.generateJwtToken("alice.smith@test.com")

        mockMvc.post("/api/swipes") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(swipeRequest)
            header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.swipeId") { value(1) }
                jsonPath("$.swipingProfile.user.firstName") { value("Alice") }
                jsonPath("$.swipedProfile.user.firstName") { value("Bob") }
                jsonPath("$.isRightSwipe") { value(true) }
            }
    }

}