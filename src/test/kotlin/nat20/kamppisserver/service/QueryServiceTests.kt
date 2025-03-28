package nat20.kamppisserver.service

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.repository.UserProfileRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
class QueryServiceTests @Autowired constructor(
    val queryService: QueryService,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository
) {

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

        val listOfUserProfileDTOs: List<UserProfileDTO>? = userProfile.id?.let {
            queryService.findUserProfilesThatMeetCriteria(
                it
            )
        }

        if (listOfUserProfileDTOs != null) {
            assertFalse(userProfileDTO in listOfUserProfileDTOs)
        }
    }
}