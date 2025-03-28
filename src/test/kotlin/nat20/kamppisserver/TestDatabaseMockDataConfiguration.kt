package nat20.kamppisserver

import nat20.kamppisserver.configuration.mockdataconfigs.*
import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.*
import nat20.kamppisserver.repository.*
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.LocalDate

/**
 * DataInitializer for test classes. The same data can be found in
 * nat20.kamppisserver.configuration.DatabaseMockDataConfiguration.
 */
@Configuration
@Profile("test")
class TestDatabaseMockDataConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            profileRepository: ProfileRepository,
                            profilePhotoRepository: ProfilePhotoRepository,
                            flatRepository: FlatRepository,
                            roommatePreferenceRepository: RoommatePreferenceRepository,
                            roomPreferenceRepository: RoomPreferenceRepository,
                            matchRepository: MatchRepository,
                            swipeRepository: SwipeRepository
    ) = ApplicationRunner {
        println("Initializing test database with mock data...")

        // Add mock users to database
        val mockUsersConfig = MockUsersConfig()
        mockUsersConfig.insertMockUsersToDatabase(userRepository)

        // Add mock flats to database
        val mockFlatsConfig = MockFlatsConfig()
        mockFlatsConfig.insertMockFlatsToDatabase(flatRepository)

        // Add mock user profiles to database
        val mockUserProfilesConfig = MockUserProfilesConfig()
        mockUserProfilesConfig.insertMockUserProfilesToDatabase(userRepository, profileRepository)

        // Add mock room profiles to database
        val mockRoomProfilesConfig = MockRoomProfilesConfig()
        mockRoomProfilesConfig.insertMockRoomProfilesToDatabase(userRepository,flatRepository, profileRepository)

        // Add mock profile photos to database
        val mockProfilePhotosConfig = MockProfilePhotosConfig()
        mockProfilePhotosConfig.insertMockProfilePhotosToDatabase(profileRepository, profilePhotoRepository)

        // Add mock roommate preferences to database
        val mockRoommatePreferencesConfig = MockRoommatePreferencesConfig()
        mockRoommatePreferencesConfig.insertMockRoommatePreferencesToDatabase(userRepository, roommatePreferenceRepository)

        // Add mock room preferences to database
        val mockRoomPreferencesConfig = MockRoomPreferencesConfig()
        mockRoomPreferencesConfig.insertMockRoomPreferencesToDatabase(userRepository, roomPreferenceRepository)

        // Add mock swipes to database
        val mockSwipesConfig = MockSwipesConfig()
        mockSwipesConfig.insertMockSwipesToDatabase(profileRepository, swipeRepository)

        // Add mock matches to database
        val mockMatchesConfig = MockMatchesConfig()
        mockMatchesConfig.insertMockMatchesToDatabase(userRepository, matchRepository)

        println("\u2705 Mock data inserted successfully!")
    }
}