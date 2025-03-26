package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.repository.SwipeRepository

class MockSwipesConfig {

    /**
     * Database initializer for adding mock swipes data into the database
     */
    fun insertMockSwipesToDatabase(
        profileRepository: ProfileRepository,
        swipeRepository: SwipeRepository
    ) {
        // These are swipes that profiles have given to each other
        val swipes = listOf(
            Swipe(profileRepository.findById(1L).get(), profileRepository.findById(2L).get(), true),
            Swipe(profileRepository.findById(1L).get(), profileRepository.findById(3L).get(), true),
            Swipe(profileRepository.findById(2L).get(), profileRepository.findById(1L).get(), true),
            Swipe(profileRepository.findById(3L).get(), profileRepository.findById(1L).get(), true)
        )

        swipeRepository.saveAll(swipes)
    }
}