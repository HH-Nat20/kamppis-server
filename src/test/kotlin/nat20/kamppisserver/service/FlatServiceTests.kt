package nat20.kamppisserver.service

import nat20.kamppisserver.domain.Flat
import nat20.kamppisserver.domain.FlatDTO
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.domain.enums.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import kotlin.test.*

/**
 * Test class for FlatService.
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS )
@Transactional
class FlatServiceTests @Autowired constructor(
    val flatService: FlatService,
    val roomProfileService: RoomProfileService
) {
    /*
     * Here we declare variables that are used in every test
     */
    lateinit var testFlatRequest: FlatDTO
    lateinit var testRoomProfileRequest: RoomProfileRequest
    lateinit var addedFlat: FlatDTO

    @BeforeEach
    fun testVariableSetUp() {
        testFlatRequest = Flat(
            name = "McMansion",
            description= "Seinät muovia, aidat muovia, puut muovia.",
            location = City.ESPOO,
            totalRoommates = 13,
            petHousehold = null,
            flatUtilities = mutableListOf(Utilities.WIFI, Utilities.BALCONY),
            roomProfiles = null
        ).toDTO()

        addedFlat = flatService.add(testFlatRequest)

        testRoomProfileRequest = RoomProfileRequest(
            userIds = listOf(1L, 2L), // User with id 2 has Pets.PET_OWNER
            flatId = addedFlat.id!!,
            rent = 750,
            isPrivateRoom = true,
            furnished = true,
            furnishedInfo = "Heinäpaalisänky",
            bio = "Kiva lato. Heinäpaalit kullekin."
        )
    }

    @Test
    fun `flat's pet household status should be false by default when added to database`() {
        val petHouseholdStatus = flatService.findById(addedFlat.id!!).petHousehold

        assertFalse(petHouseholdStatus!!)
    }

    @Test
    fun `should update flat's pet household status to true when a room profile with a pet owner user is added to flat`() {
        roomProfileService.add(testRoomProfileRequest)
        val petHouseholdStatus = flatService.findById(addedFlat.id!!).petHousehold

        assertTrue(petHouseholdStatus!!)
    }

    @Test
    fun `should update flat's pet household status to false when a pet owner user is removed from all the room profiles in the flat`() {
        val updateRoomProfileRequest = RoomProfileRequest(
            userIds = listOf(1L),
            flatId = addedFlat.id!!,
            rent = 750,
            isPrivateRoom = true,
            furnished = true,
            furnishedInfo = "Heinäpaalisänky",
            bio = "Kiva lato. Kämppis ja koira lähti."
        )

        val addedRoomProfile = roomProfileService.add(testRoomProfileRequest)
        roomProfileService.update(updateRoomProfileRequest, addedRoomProfile.id!!)
        val petHouseholdStatus = flatService.findById(addedFlat.id!!).petHousehold

        assertFalse(petHouseholdStatus!!)
    }

    @Test
    fun `should update flat's pet household status to false when a all room profiles are removed from the flat`() {
        val addedRoomProfile = roomProfileService.add(testRoomProfileRequest)
        roomProfileService.delete(addedRoomProfile.id!!)
        val petHouseholdStatus = flatService.findById(addedFlat.id!!).petHousehold

        assertFalse(petHouseholdStatus!!)
    }
}