package nat20.kamppisserver.configuration

import nat20.kamppisserver.configuration.mockdataconfigs.*
import nat20.kamppisserver.repository.*
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("dev", "prod") // This config will only load when the dev or prod profile is active from application.properties
class DatabaseMockDataConfiguration {

    /**
     * Database initializer for adding mock data into the database
     */
    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        profileRepository: ProfileRepository,
        profilePhotoRepository: ProfilePhotoRepository,
        flatRepository: FlatRepository,
        roommatePreferenceRepository: RoommatePreferenceRepository,
        roomPreferenceRepository: RoomPreferenceRepository,
        matchRepository: MatchRepository,
        swipeRepository: SwipeRepository,
    ) = ApplicationRunner {

        // Application.properties: spring.jpa.hibernate.ddl-auto=create, tables are always dropped and re-created
        // THIS WILL NEVER RUN
        // Check if users table has any entries
        if (userRepository.count() > 0) {
            println("Database already initialized, skipping mock data insertion.")
            return@ApplicationRunner
        }

        println("Initializing database with mock data...")

        // Add mock users to database
        val mockUsersConfig = MockUsersConfig()
        mockUsersConfig.insertMockUsersToDatabase(userRepository)

        // Add mock flats to database
        val mockFlatsConfig = MockFlatsConfig()
        mockFlatsConfig.insertMockFlatsToDatabase(flatRepository)

        // Add mock user profiles to database
        val mockUserProfilesConfig = MockUserProfilesConfig()
        mockUserProfilesConfig.insertMockUserProfilesToDatabase(userRepository, profileRepository)

        /**
        // Add mock room profiles to database
        val mockRoomProfilesConfig = MockRoomProfilesConfig()
        mockRoomProfilesConfig.insertMockRoomProfilesToDatabase(userRepository,flatRepository, profileRepository)
 */
        // Add mock profile photos to database
        val mockProfilePhotosConfig = MockProfilePhotosConfig()
        mockProfilePhotosConfig.insertMockProfilePhotosToDatabase(profileRepository, profilePhotoRepository)

        // Add mock roommate preferences to database
        val mockRoommatePreferencesConfig = MockRoommatePreferencesConfig()
        mockRoommatePreferencesConfig.insertMockRoommatePreferencesToDatabase(userRepository, roommatePreferenceRepository)

        // TODO: Add mock room preferences to database

        // Add mock swipes to database
        val mockSwipesConfig = MockSwipesConfig()
        mockSwipesConfig.insertMockSwipesToDatabase(userRepository, swipeRepository)

        // Add mock matches to database
        val mockMatchesConfig = MockMatchesConfig()
        mockMatchesConfig.insertMockMatchesToDatabase(userRepository, matchRepository)

        println("\u2705 Mock data inserted successfully!")
    }
}