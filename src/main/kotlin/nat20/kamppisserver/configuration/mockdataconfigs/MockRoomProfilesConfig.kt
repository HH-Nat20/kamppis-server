package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.repository.FlatRepository
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.repository.UserRepository

class MockRoomProfilesConfig {

    /**
     * Database initializer for adding mock room profile data into the database

    fun insertMockRoomProfilesToDatabase(
        userRepository: UserRepository,
        flatRepository: FlatRepository,
        profileRepository: ProfileRepository
    ) {
        val roomProfiles = listOf(
            RoomProfile(
                user = mutableListOf(userRepository.findById(47L).get(), userRepository.findById(48L).get()),
                flat = flatRepository.findById(1L).get(),
                rent = 450,
                isPrivateRoom = true,
                bio = "Kokonaiset 9 neliötä, ikkunoissa kalterit. Pehmustetut seinät.",
            ),
            RoomProfile(
                user = mutableListOf(userRepository.findById(47L).get(), userRepository.findById(48L).get()),
                flat = flatRepository.findById(1L).get(),
                rent = 600,
                isPrivateRoom = true,
                bio = "20 neliötä merinäköalalla. Avara näkymä. (Ei sisällä seiniä tai kattoa).")
        )

        profileRepository.saveAll(roomProfiles)
    }*/
}