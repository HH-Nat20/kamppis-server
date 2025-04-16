package nat20.kamppisserver.service

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.repository.RoommatePreferenceRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.repository.UserProfileRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertFalse

/**
 * Test class for QueryService.
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class QueryServiceTest @Autowired constructor(
    val queryService: QueryService,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository
) {

    @Autowired
    private lateinit var roommatePreferenceRepository: RoommatePreferenceRepository

    @Test
    fun `should return the correct UserProfile from UserProfileRepository by User-objects id`() {
        val user: User = userRepository.findByIdOrNull(1L)!!
        val userProfile = user.id?.let { queryService.findUserProfileByUserId(it) }
        assertEquals(1L, userProfile?.id)
    }

    @Test
    fun `should not return the querying user's UserProfile`() {
        val userProfile: UserProfile = userProfileRepository.findByIdOrNull(1L)!!
        val userProfileDTO: UserProfileDTO = userProfile.toDTO()

        val listOfUserProfileDTOs: Page<UserProfileDTO> = queryService.findUserProfilesThatMeetCriteria(PageRequest.of(0, 25), userProfile.user.id!!)

        if (listOfUserProfileDTOs != null) {
            assertFalse(userProfileDTO in listOfUserProfileDTOs)
        }
    }
}