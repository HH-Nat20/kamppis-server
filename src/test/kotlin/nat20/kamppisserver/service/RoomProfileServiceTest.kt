package nat20.kamppisserver.service

import io.mockk.*
import nat20.kamppisserver.domain.Flat
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.enums.ProfileStatus
import nat20.kamppisserver.repository.FlatRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.setup.StandaloneSetup
import exception.EntityNotFoundException
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.domain.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class RoomProfileServiceTest {

    private lateinit var roomProfileRepository: RoomProfileRepository
    private lateinit var userRepository: UserRepository
    private lateinit var flatRepository: FlatRepository
    private lateinit var flatService: FlatService
    private lateinit var roomProfileService: RoomProfileService

    lateinit var roomProfile: RoomProfile
    lateinit var request: RoomProfileRequest
    lateinit var user: User
    lateinit var flat: Flat

    @BeforeEach
    fun setup() {
        roomProfileRepository = mockk()
        userRepository = mockk()
        flatRepository = mockk()
        flatService = mockk(relaxed = true)
        roomProfileService = RoomProfileService(roomProfileRepository, userRepository, flatRepository, flatService)

        StandaloneSetup.setup()
        roomProfile = StandaloneSetup.roomProfile
        request = StandaloneSetup.request
        user = StandaloneSetup.user1
        flat = StandaloneSetup.flat1
    }

    @Test
    fun `findAll should return list of RoomProfileDTO`() {
        every { roomProfileRepository.findAllActive() } returns listOf(roomProfile)

        val result = roomProfileService.findAll()

        assertEquals(1, result.size)
        verify { roomProfileRepository.findAllActive() }
    }

    @Test
    fun `findById should return RoomProfileDTO when found`() {
        every { roomProfileRepository.findByIdActive(roomProfile.id!!) } returns roomProfile

        val result = roomProfileService.findById(roomProfile.id!!)

        assertNotNull(result)
        verify { roomProfileRepository.findByIdActive(roomProfile.id!!) }
    }

    @Test
    fun `findById should throw EntityNotFoundException when not found`() {
        every { roomProfileRepository.findByIdActive(roomProfile.id!!) } returns null

        assertThrows<EntityNotFoundException> {
            roomProfileService.findById(roomProfile.id!!)
        }
        verify { roomProfileRepository.findByIdActive(roomProfile.id!!) }
    }

    @Test
    fun `add should create and return new RoomProfileDTO`() {
        every { userRepository.findById(user.id!!).get() } returns user
        every { flatRepository.findById(flat.id!!).get() } returns flat
        every { roomProfileRepository.save(any()) } returns roomProfile
        every { flatService.updatePetHouseholdStatus(any(), any()) } just Runs

        val result = roomProfileService.add(request)

        assertNotNull(result)
        verify { roomProfileRepository.save(any()) }
        verify { flatService.updatePetHouseholdStatus(flat.id!!, roomProfile.id!!) }
    }

    @Test
    fun `update should modify existing RoomProfile`() {
        every { roomProfileRepository.findByIdActive(roomProfile.id!!) } returns roomProfile
        every { userRepository.findById(user.id!!).get() } returns user
        every { flatRepository.findById(flat.id!!).get() } returns flat
        every { roomProfileRepository.saveAndFlush(roomProfile) } returns roomProfile
        every { flatService.updatePetHouseholdStatus(any(), any()) } just Runs

        val result = roomProfileService.update(request, roomProfile.id!!)

        assertNotNull(result)
        verify { roomProfileRepository.saveAndFlush(roomProfile) }
        verify { flatService.updatePetHouseholdStatus(any(), any()) }
    }

    @Test
    fun `update should throw EntityNotFoundException if profile does not exist`() {
        every { roomProfileRepository.findByIdActive(999L) } returns null

        assertThrows<EntityNotFoundException> {
            roomProfileService.update(request, 999L)
        }
    }

    @Test
    fun `delete should mark profile as INACTIVE`() {
        every { roomProfileRepository.findByIdActive(roomProfile.id!!) } returns roomProfile
        every { roomProfileRepository.save(roomProfile) } returns roomProfile
        every { flatService.updatePetHouseholdStatus(flat.id!!, roomProfile.id!!) } just Runs

        roomProfileService.delete(roomProfile.id!!)

        verify { roomProfileRepository.save(roomProfile) }
        verify { flatService.updatePetHouseholdStatus(flat.id!!, roomProfile.id!!) }
        assertEquals(ProfileStatus.INACTIVE, roomProfile.status)
        assertNotNull(roomProfile.deletedAt)
    }

    @Test
    fun `delete should throw EntityNotFoundException if profile not found`() {
        every { roomProfileRepository.findByIdActive(999L) } returns null

        assertThrows<EntityNotFoundException> {
            roomProfileService.delete(999L)
        }
    }

    @Test
    fun `findUsersRoomProfiles should return list from repository`() {
        val profiles = listOf(roomProfile)
        every { roomProfileRepository.findUsersRoomProfiles(roomProfile.id!!, user.id!!) } returns profiles

        val result = roomProfileService.findUsersRoomProfiles(roomProfile.id!!, user.id!!)

        assertEquals(profiles, result)
    }
}