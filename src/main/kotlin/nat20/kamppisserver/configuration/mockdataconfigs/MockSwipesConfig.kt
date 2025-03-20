package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.repository.SwipeRepository
import nat20.kamppisserver.repository.UserRepository

class MockSwipesConfig {

    /**
     * Database initializer for adding mock swipes data into the database
     */
    fun insertMockSwipesToDatabase(
        userRepository: UserRepository,
        swipeRepository: SwipeRepository
    ) {
        // These are swipes that users have given to each other
        val swipes = listOf(
            Swipe(userRepository.findById(1L).get(), userRepository.findById(2L).get(), true),
            Swipe(userRepository.findById(1L).get(), userRepository.findById(3L).get(), true),
            Swipe(userRepository.findById(2L).get(), userRepository.findById(1L).get(), true),
            Swipe(userRepository.findById(3L).get(), userRepository.findById(1L).get(), true)
        )

        swipeRepository.saveAll(swipes)
    }
}