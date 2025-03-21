package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.UserRepository

class MockMatchesConfig {

    /**
     * Database initializer for adding mock match data into the database
     */
    fun insertMockMatchesToDatabase(
        userRepository: UserRepository,
        matchRepository: MatchRepository,
        ) {
        // These are matches for users who have swiped each other
        val matches = listOf(
            Match(
                users = mutableSetOf(userRepository.findById(1L).get(), userRepository.findById(2L).get())
            ),
            Match(
                users = mutableSetOf(userRepository.findById(1L).get(), userRepository.findById(3L).get())
            )
        )

        // Save matches to database
        matchRepository.saveAll(matches)
    }
}