package nat20.kamppisserver.service

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.UserRepository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles

/**
 * Test class for QueryService.
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryServiceTests {

    @Autowired
    private lateinit var queryService: QueryService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should return the correct UserProfile from UserProfileRepository by User-objects id`() {
        val user: User? = userRepository.findByIdOrNull(1L)
        val userProfile = queryService.findUserProfileByUserId(user?.id)
        assertEquals(1L, userProfile?.id)
    }
}