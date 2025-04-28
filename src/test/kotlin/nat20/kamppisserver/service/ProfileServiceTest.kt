package nat20.kamppisserver.service

import io.mockk.*
import exception.EntityNotFoundException
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.setup.StandaloneSetup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProfileServiceTest {

    private lateinit var roomProfileRepository: RoomProfileRepository
    private lateinit var userProfileRepository: UserProfileRepository
    private lateinit var profileService: ProfileService

    lateinit var roomProfile: RoomProfile
    lateinit var userProfile: UserProfile

    @BeforeEach
    fun setup() {
        roomProfileRepository = mockk()
        userProfileRepository = mockk()
        profileService = ProfileService(roomProfileRepository, userProfileRepository)

        StandaloneSetup.setup()
        roomProfile = StandaloneSetup.roomProfile
        userProfile = StandaloneSetup.userProfile
    }

    @Test
    fun `findAll should return combined list of profiles`() {
        every { roomProfileRepository.findAllActive() } returns listOf(roomProfile)
        every { userProfileRepository.findAllActive() } returns listOf(userProfile)

        val result = profileService.findAll()

        assertEquals(2, result.size)
        verify { roomProfileRepository.findAllActive() }
        verify { userProfileRepository.findAllActive() }
    }

    @Test
    fun `findById should return RoomProfile when found`() {
        every { roomProfileRepository.findByIdActive(roomProfile.id!!) } returns roomProfile
        every { userProfileRepository.findByIdActive(userProfile.id!!) } returns null

        val result = profileService.findById(roomProfile.id!!)

        assertNotNull(result)
        verify { roomProfileRepository.findByIdActive(roomProfile.id!!) }
        verify { userProfileRepository.findByIdActive(userProfile.id!!) }
    }

    @Test
    fun `findById should return UserProfile when RoomProfile not found`() {
        every { roomProfileRepository.findByIdActive(roomProfile.id!!) } returns null
        every { userProfileRepository.findByIdActive(userProfile.id!!) } returns userProfile

        val result = profileService.findById(userProfile.id!!)

        assertNotNull(result)
        verify { roomProfileRepository.findByIdActive(roomProfile.id!!) }
        verify { userProfileRepository.findByIdActive(userProfile.id!!) }
    }

    @Test
    fun `findById should throw EntityNotFoundException when neither RoomProfile nor UserProfile found`() {
        every { roomProfileRepository.findByIdActive(roomProfile.id!!) } returns null
        every { userProfileRepository.findByIdActive(userProfile.id!!) } returns null

        assertThrows<EntityNotFoundException> {
            profileService.findById(1L)
        }

        verify { roomProfileRepository.findByIdActive(roomProfile.id!!) }
        verify { userProfileRepository.findByIdActive(userProfile.id!!) }
    }
}