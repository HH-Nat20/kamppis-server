package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.repository.FlatRepository
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.repository.UserRepository

class MockRoomProfilesConfig {

    /**
     * Database initializer for adding mock room profile data into the database
    */
    fun insertMockRoomProfilesToDatabase(
        userRepository: UserRepository,
        flatRepository: FlatRepository,
        profileRepository: ProfileRepository
    ) {
        val roomProfiles = listOf(
            RoomProfile( //#79
                users = mutableListOf(userRepository.findById(53L).get(), userRepository.findById(54L).get()),
                flat = flatRepository.findById(1L).get(),
                name = "Spacious room",
                rent = 450,
                isPrivateRoom = true,
                bio = "Spacious private room with a large window offering plenty of natural light. Furnished with a comfortable bed, desk, and wardrobe.",
                furnished = true,
                furnishedInfo = "Bed, desk, wardobe"
            ),
            RoomProfile( //#80
                users = mutableListOf(userRepository.findById(53L).get(), userRepository.findById(54L).get()),
                flat = flatRepository.findById(1L).get(),
                name = "Cozy room",
                rent = 600,
                isPrivateRoom = true,
                bio = "Cozy private room with a balcony access. Perfect for enjoying fresh air and relaxing outdoors. Includes a bed, nightstand, and closet.",
                furnished = true,
                furnishedInfo = "Bed, nightstand, closet"
            ),
            RoomProfile( //#81
                users = mutableListOf(userRepository.findById(55L).get()),
                flat = flatRepository.findById(2L).get(),
                name = "Modern room",
                rent = 700,
                isPrivateRoom = true,
                bio = "Modern private room with an en-suite bathroom.",
                furnished = false,
                furnishedInfo = null
            ),
            RoomProfile( //#82
                users = mutableListOf(userRepository.findById(56L).get()),
                flat = flatRepository.findById(3L).get(),
                name = "Bright room",
                rent = 550,
                isPrivateRoom = true,
                bio = "Bright private room with a sea-side view. Furnished with a single bed, desk, and bookshelf. Ideal for quiet study sessions.",
                furnished = true,
                furnishedInfo = "Bed, desk, bookshelf"
            ),
            RoomProfile( //#83
                users = mutableListOf(userRepository.findById(56L).get()),
                flat = flatRepository.findById(3L).get(),
                name = "Comfortable room",
                rent = 450,
                isPrivateRoom = true,
                bio = "Comfortable private room with a large wardrobe and a cozy reading nook. Includes a double bed and a desk.",
                furnished = true,
                furnishedInfo = "Double bed, desk"
            ),
            RoomProfile( //#84
                users = mutableListOf(userRepository.findById(57L).get(), userRepository.findById(58L).get(), userRepository.findById(59L).get()),
                flat = flatRepository.findById(4L).get(),
                rent = 650,
                isPrivateRoom = true,
                bio = "Private room with a separate bathroom and shower. Features a spacious layout with a bed, dresser, and study table.",
                furnished = true,
                furnishedInfo = "Bed, dresser, table"
            ),
            RoomProfile( //#85
                users = mutableListOf(userRepository.findById(60L).get(), userRepository.findById(61L).get()),
                flat = flatRepository.findById(5L).get(),
                rent = 650,
                isPrivateRoom = true,
                bio = "Private room with a balcony and city view. Perfect for enjoying the urban scenery.",
                furnished = false,
                furnishedInfo = null
            ),
            RoomProfile( //#86
                users = mutableListOf(userRepository.findById(62L).get(), userRepository.findById(63L).get()),
                flat = flatRepository.findById(6L).get(),
                rent = 550,
                isPrivateRoom = true,
                bio = "Private room with a laundry machine access. Includes a bed, nightstand, and closet. Convenient for keeping your clothes fresh.",
                furnished = true,
                furnishedInfo = "Bed, nightstand, closet"
            ),
            RoomProfile( //#87
                users = mutableListOf(userRepository.findById(64L).get()),
                flat = flatRepository.findById(7L).get(),
                rent = 550,
                isPrivateRoom = true,
                bio = "Private room with a modern design and WiFi access.",
                furnished = false,
                furnishedInfo = null
            ),
            RoomProfile( //#88
                users = mutableListOf(userRepository.findById(65L).get(), userRepository.findById(66L).get()),
                flat = flatRepository.findById(8L).get(),
                rent = 500,
                isPrivateRoom = true,
                bio = "Private room with a park view. Furnished with a bed, desk, and wardrobe. Ideal for nature lovers.",
                furnished = true,
                furnishedInfo = "Bed, desk, wardrobe"
            ),
            RoomProfile( //#89
                users = mutableListOf(userRepository.findById(67L).get(), userRepository.findById(68L).get(), userRepository.findById(69L).get()),
                flat = flatRepository.findById(9L).get(),
                rent = 650,
                isPrivateRoom = true,
                bio = "Private room with a dishwasher access. Convenient for easy meal cleanup.",
                furnished = false,
                furnishedInfo = null
            ),
            RoomProfile( //#90
                users = mutableListOf(userRepository.findById(70L).get(), userRepository.findById(71L).get()),
                flat = flatRepository.findById(10L).get(),
                name = "Skyline room",
                rent = 700,
                isPrivateRoom = true,
                bio = "Private room with a balcony and skyline view. Features a bed, desk, and wardrobe. Perfect for enjoying the city lights.",
                furnished = true,
                furnishedInfo = "Bed, desk, wardrobe"
            ),
            RoomProfile( //#91
                users = mutableListOf(userRepository.findById(72L).get()),
                flat = flatRepository.findById(11L).get(),
                name = "Private room",
                rent = 750,
                isPrivateRoom = true,
                bio = "Private room with a private bathroom. Furnished with a bed, desk, and wardrobe. Ideal for those who value privacy.",
                furnished = true,
                furnishedInfo = "Bed, desk, wardrobe"
            ),
            RoomProfile( //#92
                users = mutableListOf(userRepository.findById(72L).get()),
                flat = flatRepository.findById(11L).get(),
                rent = 550,
                isPrivateRoom = true,
                bio = "Private room with a modern design and WiFi access.",
                furnished = false,
                furnishedInfo = null
            ),
            RoomProfile( //#93
                users = mutableListOf(userRepository.findById(73L).get()),
                flat = flatRepository.findById(12L).get(),
                name = "Shared room",
                rent = 450,
                isPrivateRoom = false,
                bio = "Shared room with two single beds and a large window. Includes desks and wardrobes for each roommate.",
                furnished = true,
                furnishedInfo = "Bed, desk, wardrobe"
            ),
            RoomProfile( //#94
                users = mutableListOf(userRepository.findById(74L).get()),
                flat = flatRepository.findById(13L).get(),
                rent = 450,
                isPrivateRoom = false,
                bio = "Shared room with bunk beds and a cozy reading nook. Features storage space for each roommate.",
                furnished = false,
                furnishedInfo = null
            ),
            RoomProfile( //#95
                users = mutableListOf(userRepository.findById(75L).get(), userRepository.findById(76L).get()),
                flat = flatRepository.findById(14L).get(),
                rent = 450,
                isPrivateRoom = false,
                bio = "Shared room with twin beds and a balcony access. Includes desks and wardrobes for each roommate.",
                furnished = true,
                furnishedInfo = "Desk, wardrobe"
            ),
            RoomProfile( //#96
                users = mutableListOf(userRepository.findById(77L).get(), userRepository.findById(78L).get()),
                flat = flatRepository.findById(15L).get(),
                rent = 450,
                isPrivateRoom = false,
                bio = "Shared room with and a sea-side view.",
                furnished = false,
                furnishedInfo = null
            )
        )

        profileRepository.saveAll(roomProfiles)
    }
}