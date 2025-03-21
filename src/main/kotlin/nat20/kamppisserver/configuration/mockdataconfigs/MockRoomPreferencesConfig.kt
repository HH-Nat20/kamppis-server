package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.RoomPreference
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.repository.RoomPreferenceRepository
import nat20.kamppisserver.repository.UserRepository

class MockRoomPreferencesConfig {

    /**
     * Database initializer for adding mock room preferences data into the database
     */
    fun insertMockRoomPreferencesToDatabase(
        userRepository: UserRepository,
        roomPreferenceRepository: RoomPreferenceRepository
    ) {
        val roomPreferences = listOf(
            RoomPreference(
                user = userRepository.findById(27L).get(),
                maxRent = 750,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(28L).get(),
                maxRent = 800,
                hasPrivateRoom = true,
                maxRoommates = 2,
                locationPreferences = mutableListOf(City.VANTAA, City.HELSINKI)
            ),
            RoomPreference(
                user = userRepository.findById(29L).get(),
                maxRent = 700,
                hasPrivateRoom = false,
                maxRoommates = 4,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(30L).get(),
                maxRent = 850,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO)
            ),
            RoomPreference(
                user = userRepository.findById(31L).get(),
                maxRent = 675,
                hasPrivateRoom = true,
                maxRoommates = 2,
                locationPreferences = mutableListOf(City.VANTAA, City.HELSINKI)
            ),
            RoomPreference(
                user = userRepository.findById(32L).get(),
                maxRent = 725,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.ESPOO, City.HELSINKI)
            ),
            RoomPreference(
                user = userRepository.findById(33L).get(),
                maxRent = 800,
                hasPrivateRoom = false,
                maxRoommates = 4,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(34L).get(),
                maxRent = 750,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(35L).get(),
                maxRent = 825,
                hasPrivateRoom = true,
                maxRoommates = 2,
                locationPreferences = mutableListOf(City.ESPOO, City.HELSINKI)
            ),
            RoomPreference(
                user = userRepository.findById(36L).get(),
                maxRent = 700,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.VANTAA, City.ESPOO)
            ),
            RoomPreference(
                user = userRepository.findById(37L).get(),
                maxRent = 750,
                hasPrivateRoom = false,
                maxRoommates = 4,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(38L).get(),
                maxRent = 800,
                hasPrivateRoom = true,
                maxRoommates = 2,
                locationPreferences = mutableListOf(City.ESPOO, City.HELSINKI)
            ),
            RoomPreference(
                user = userRepository.findById(39L).get(),
                maxRent = 675,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.VANTAA, City.ESPOO)
            ),
            RoomPreference(
                user = userRepository.findById(40L).get(),
                maxRent = 725,
                hasPrivateRoom = true,
                maxRoommates = 2,
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(41L).get(),
                maxRent = 850,
                hasPrivateRoom = false,
                maxRoommates = 4,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(42L).get(),
                maxRent = 750,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.VANTAA, City.ESPOO)
            ),
            RoomPreference(
                user = userRepository.findById(43L).get(),
                maxRent = 800,
                hasPrivateRoom = true,
                maxRoommates = 2,
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(44L).get(),
                maxRent = 700,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(45L).get(),
                maxRent = 825,
                hasPrivateRoom = false,
                maxRoommates = 4,
                locationPreferences = mutableListOf(City.VANTAA, City.ESPOO)
            ),
            RoomPreference(
                user = userRepository.findById(46L).get(),
                maxRent = 750,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(47L).get(),
                maxRent = 675,
                hasPrivateRoom = true,
                maxRoommates = 2,
                locationPreferences = mutableListOf(City.ESPOO, City.HELSINKI)
            ),
            RoomPreference(
                user = userRepository.findById(48L).get(),
                maxRent = 725,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.VANTAA, City.ESPOO)
            ),
            RoomPreference(
                user = userRepository.findById(49L).get(),
                maxRent = 800,
                hasPrivateRoom = false,
                maxRoommates = 4,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            ),
            RoomPreference(
                user = userRepository.findById(50L).get(),
                maxRent = 750,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.ESPOO, City.HELSINKI)
            ),
            RoomPreference(
                user = userRepository.findById(51L).get(),
                maxRent = 850,
                hasPrivateRoom = true,
                maxRoommates = 2,
                locationPreferences = mutableListOf(City.VANTAA, City.ESPOO)
            ),
            RoomPreference(
                user = userRepository.findById(52L).get(),
                maxRent = 700,
                hasPrivateRoom = true,
                maxRoommates = 3,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA)
            )
        )

        roomPreferenceRepository.saveAll(roomPreferences)
    }
}