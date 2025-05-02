package nat20.kamppisserver.setup

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.Utilities
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Sets up test data for use in WebMVC (.api package) and unit (.service package) tests.
 */
object StandaloneSetup {

    lateinit var flat1: Flat
    lateinit var flat2: Flat
    lateinit var user1: User
    lateinit var user2: User
    lateinit var userProfile: UserProfile
    lateinit var userProfileRequest: UserProfileRequest
    lateinit var match: Match
    lateinit var roomProfile: RoomProfile
    lateinit var invite: RoomProfileInvite
    lateinit var inviteResponse: InviteResponse
    lateinit var request: RoomProfileRequest
    lateinit var loginResponse: Map<String, String>
    lateinit var feedback: Feedback

    fun setup() {
        flat1 = Flat(
            id = 1,
            name = "Nice place",
            description = "Sunny",
            location = City.HELSINKI,
            totalRoommates = 3,
            petHousehold = false,
            flatUtilities = mutableListOf(Utilities.WIFI)
        )

        flat2 = Flat(
            id = 2,
            name = "Cozy loft",
            description = "Downtown",
            location = City.TAMPERE,
            totalRoommates = 2,
            petHousehold = true,
            flatUtilities = mutableListOf(Utilities.LAUNDRY_MACHINE)
        )

        user1 = User(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            gender = Gender.MALE,
            id = 1L
        )

        user2 = User(
            firstName = "Jane",
            lastName = "Doe",
            email = "jane.doe@example.com",
            dateOfBirth = LocalDate.of(1990, 1, 1),
            gender = Gender.FEMALE,
            id = 2L
        )

        userProfile = UserProfile(
            user = user1,
            bio = "Test bio",
            photos = mutableListOf()
        )
        userProfile.id = 1L

        userProfileRequest = UserProfileRequest(
            userId = user1.id!!,
            bio = "Test bio"
        )

        match = Match(
            users = mutableSetOf(user1, user2),
            id = 1L
        )

        roomProfile = RoomProfile(
            users = mutableListOf(user1),
            flat = flat1,
            rent = 500,
            isPrivateRoom = true,
            furnished = false
        )
        roomProfile.id = 1L

        invite = RoomProfileInvite(
            roomProfileId = 123L,
            roomProfileInviteToken = "INV123",
            expiresAt = LocalDateTime.now().plusDays(1)
        )

        inviteResponse = InviteResponse(
            inviteToken = "INV123",
            expiresAt = LocalDateTime.now().plusDays(1),
            message = null
        )

        request = RoomProfileRequest(
            userIds = listOf(user1.id!!),
            flatId = flat1.id!!,
            name = "Test room",
            rent = 400,
            isPrivateRoom = false,
            furnished = false,
            furnishedInfo = null,
            bio = "Chill area",
            id = 1L
        )

        loginResponse = emptyMap()

        feedback = Feedback(
            feedback = "Ain't this a surprise"
        )
    }
}