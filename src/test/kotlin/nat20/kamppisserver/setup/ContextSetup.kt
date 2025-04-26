package nat20.kamppisserver.setup

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.*
import nat20.kamppisserver.repository.*
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Sets up and populates a mini database for @DataJPATest classes (.repository package).
 */
class ContextSetup(
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository,
    val flatRepository: FlatRepository,
    val roomProfileRepository: RoomProfileRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository
) {

    lateinit var user: User
    lateinit var swipingUser: User
    lateinit var deletedUser: User
    lateinit var userProfile: UserProfile
    lateinit var swipingUserProfile: UserProfile
    lateinit var deletedUserProfile: UserProfile
    lateinit var flat: Flat
    lateinit var roomProfile: RoomProfile
    lateinit var swipingRoomProfile: RoomProfile
    lateinit var deletedRoomProfile: RoomProfile
    lateinit var roomPreference: RoomPreference
    lateinit var roommatePreference: RoommatePreference
    lateinit var swipingRoommatePreference: RoommatePreference

    fun setup() {
        user = userRepository.save(
            User(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                dateOfBirth = LocalDate.of(1980, 1, 1),
                gender = Gender.MALE,
                lookingFor = LookingFor.OTHER_USER_PROFILES
            )
        )

        swipingUser = userRepository.save(
            User(
                firstName = "Jane",
                lastName = "Doe",
                email = "jane.doe@example.com",
                dateOfBirth = LocalDate.of(1990, 1, 1),
                gender = Gender.FEMALE,
                lookingFor = LookingFor.OTHER_USER_PROFILES
            )
        )

        deletedUser = User(
            firstName = "Deleted",
            lastName = "Doe",
            email = "deleted.doe@example.com",
            dateOfBirth = LocalDate.of(2000, 1, 1),
            gender = Gender.OTHER,
            lookingFor = LookingFor.OTHER_USER_PROFILES
        )

        deletedUser.status = UserStatus.INACTIVE
        deletedUser.deletedAt = LocalDateTime.now()
        userRepository.save(deletedUser)

        userProfile = userProfileRepository.save(
            UserProfile(
                user = user,
                pets = Pets.PET_OWNER
            )
        )

        swipingUserProfile = userProfileRepository.save(
            UserProfile(
                user = swipingUser
            )
        )

        deletedUserProfile = UserProfile(
            user = deletedUser
        )

        deletedUserProfile.status = ProfileStatus.INACTIVE
        deletedUserProfile.deletedAt = LocalDateTime.now()
        userProfileRepository.save(deletedUserProfile)

        flat = flatRepository.save(
            Flat(
                name = "Nice place",
                description = "Sunny",
                location = City.HELSINKI,
                totalRoommates = 3
            )
        )

        roomProfile = roomProfileRepository.save(
            RoomProfile(
                users = mutableListOf(user),
                flat = flat,
                rent = 500,
                isPrivateRoom = true,
                furnished = false
            )
        )

        swipingRoomProfile = roomProfileRepository.save(
            RoomProfile(
                users = mutableListOf(swipingUser),
                flat = flat,
                rent = 600,
                isPrivateRoom = false,
                furnished = false
            )
        )

        deletedRoomProfile = RoomProfile(
            users = mutableListOf(swipingUser),
            flat = flat,
            rent = 700,
            isPrivateRoom = true,
            furnished = false
        )

        deletedRoomProfile.status = ProfileStatus.INACTIVE
        deletedRoomProfile.deletedAt = LocalDateTime.now()
        roomProfileRepository.save(deletedRoomProfile)

        roomPreference = roomPreferenceRepository.save(
            RoomPreference(
                user = user,
                maxRent = 750,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            )
        )

        roommatePreference = roommatePreferenceRepository.save(
            RoommatePreference(
                user = user
            )
        )

        swipingRoommatePreference = roommatePreferenceRepository.save(
            RoommatePreference(
                user = swipingUser
            )
        )
    }
}
