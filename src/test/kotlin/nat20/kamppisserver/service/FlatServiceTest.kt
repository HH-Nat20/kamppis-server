package nat20.kamppisserver.service

import io.mockk.*
import nat20.kamppisserver.domain.Flat
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.repository.FlatRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.setup.StandaloneSetup
import exception.EntityNotFoundException
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.util.*

/**
 * Test class for FlatService.
 */
class FlatServiceTest {

    private lateinit var flatRepository: FlatRepository
    private lateinit var roomProfileRepository: RoomProfileRepository
    private lateinit var flatService: FlatService

    lateinit var flat1: Flat
    lateinit var roomProfile: RoomProfile

    @BeforeEach
    fun setUp() {
        flatRepository = mockk()
        roomProfileRepository = mockk()
        flatService = FlatService(flatRepository, roomProfileRepository)

        StandaloneSetup.setup()
        flat1 = StandaloneSetup.flat1
        roomProfile = StandaloneSetup.roomProfile
    }

    @Test
    fun `findAll should return list of FlatDTO`() {
        every { flatRepository.findAll() } returns listOf(flat1)

        val result = flatService.findAll()

        assertEquals(1, result.size)
        assertEquals("Nice place", result.first().name)
        verify { flatRepository.findAll() }
    }

    @Test
    fun `findById should return FlatDTO when flat exists`() {
        every { flatRepository.findById(flat1.id!!) } returns Optional.of(flat1)

        val result = flatService.findById(flat1.id!!)

        assertEquals("Nice place", result.name)
        verify { flatRepository.findById(flat1.id!!) }
    }

    @Test
    fun `findById should throw when flat not found`() {
        every { flatRepository.findById(flat1.id!!) } returns Optional.empty()

        assertThrows<EntityNotFoundException> {
            flatService.findById(flat1.id!!)
        }
        verify { flatRepository.findById(flat1.id!!) }
    }

    @Test
    fun `add should save and return FlatDTO`() {
        val request = flat1.toDTO()
        every { flatRepository.save(any()) } returns flat1

        val result = flatService.add(request)

        assertEquals(1, result.id)
        assertEquals("Nice place", result.name)
        verify { flatRepository.save(any()) }
    }

    @Test
    fun `update should modify and return updated FlatDTO`() {
        val request = Flat(
            name = "Cozy loft",
            description = "Downtown",
            location = City.TAMPERE,
            totalRoommates = 2,
            petHousehold = true,
            flatUtilities = mutableListOf(Utilities.LAUNDRY_MACHINE),
            roomProfiles = mutableListOf(roomProfile)
        ).toDTO()

        every { flatRepository.findById(flat1.id!!) } returns Optional.of(flat1)
        every { roomProfileRepository.findByIdActive(roomProfile.id!!) } returns roomProfile
        every { flatRepository.save(any()) } answers { firstArg() }

        val result = flatService.update(request, flat1.id!!)

        assertEquals("Cozy loft", result.name)
        verify { flatRepository.findById(flat1.id!!) }
        verify { roomProfileRepository.findByIdActive(roomProfile.id!!) }
        verify { flatRepository.save(any()) }
    }

    @Test
    fun `update should throw when flat not found`() {
        val request = flat1.toDTO()
        every { flatRepository.findById(flat1.id!!) } returns Optional.empty()

        assertThrows<EntityNotFoundException> {
            flatService.update(request, flat1.id!!)
        }
        verify { flatRepository.findById(flat1.id!!) }
    }

    @Test
    fun `updatePetHouseholdStatus should update petHousehold field`() {
        every { flatRepository.findById(flat1.id!!) } returns Optional.of(flat1)
        every { roomProfileRepository.findIfFlatIsPetHousehold(roomProfile.id!!) } returns true
        every { flatRepository.save(flat1) } returns flat1

        flatService.updatePetHouseholdStatus(flat1.id!!, roomProfile.id!!)

        assertTrue(flat1.petHousehold!!)
        verify { flatRepository.findById(flat1.id!!) }
        verify { roomProfileRepository.findIfFlatIsPetHousehold(roomProfile.id!!) }
        verify { flatRepository.save(flat1) }
    }

    @Test
    fun `updatePetHouseholdStatus should throw when flat not found`() {
        every { flatRepository.findById(flat1.id!!) } returns Optional.empty()

        assertThrows<EntityNotFoundException> {
            flatService.updatePetHouseholdStatus(flat1.id!!, roomProfile.id!!)
        }
        verify { flatRepository.findById(flat1.id!!) }
    }
}