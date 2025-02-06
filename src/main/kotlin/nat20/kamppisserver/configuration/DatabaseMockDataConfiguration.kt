package nat20.kamppisserver.configuration

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.domain.Gender
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.LocalDate

@Configuration
@Profile("dev") // This config will only load when the dev profile is active from application.properties
class DatabaseMockDataConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository, userProfileRepository: UserProfileRepository) = ApplicationRunner {
        val users = listOf(
            User(
                email = "alice.smith@example.com",
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 5, 14),
                null
            ),
            User(
                email = "bob.johnson@example.com",
                firstName = "Bob",
                lastName = "Johnson",
                dateOfBirth = LocalDate.of(1985, 11, 22),
                null
            ),
            User(
                email = "charlie.davis@example.com",
                firstName = "Charlie",
                lastName = "Davis",
                dateOfBirth = null, // Date of birth not provided
                null
            ),
            User(
                email = "diana.lee@example.com",
                firstName = "Diana",
                lastName = "Lee",
                dateOfBirth = LocalDate.of(2000, 2, 3),
                null
            ),
            User(
                email = "eve.brown@example.com",
                firstName = "Eve",
                lastName = "Brown",
                dateOfBirth = LocalDate.of(1995, 8, 19),
                null
            )
        )

        // Save all mock users to the database
        userRepository.saveAll(users)

        val userProfiles = listOf(
            UserProfile(
                user = userRepository.findById(1L).get(),
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(2L).get(),
                minAgePreference = 24,
                maxAgePreference = 37,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(3L).get(),
                minAgePreference = 18,
                maxAgePreference = 26,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(4L).get(),
                minAgePreference = 31,
                maxAgePreference = 38,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(5L).get(),
                minAgePreference = 40,
                maxAgePreference = 55,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
        )

        // Save all mock user profiles to the database
        userProfileRepository.saveAll(userProfiles)
    }
}