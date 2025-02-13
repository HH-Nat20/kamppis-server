package nat20.kamppisserver

import nat20.kamppisserver.domain.Gender
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Profile
import java.time.LocalDate

/**
 * DataInitializer for test classes. The same data can be found in
 * nat20.kamppisserver.configuration.DatabaseMockDataConfiguration.
 */
@TestConfiguration
@Profile("test")
class TestDatabaseMockDataConfiguration {

    @Override
    fun databaseInitializer(userRepository: UserRepository, userProfileRepository: UserProfileRepository) = ApplicationRunner {
        val users = listOf(
            User(
                email = "alice.smith@example.com",
            ),
            User(
                email = "bob.johnson@example.com",
            ),
            User(
                email = "charlie.davis@example.com",
            ),
            User(
                email = "diana.lee@example.com",
            ),
            User(
                email = "eve.brown@example.com",
            )
        )

        userRepository.saveAll(users)

        val userProfiles = listOf(
            UserProfile(
                user = userRepository.findById(1L).get(),
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 5, 14),
                gender = Gender.FEMALE,
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(2L).get(),
                firstName = "Bob",
                lastName = "Johnson",
                dateOfBirth = LocalDate.of(1985, 11, 22),
                gender = Gender.MALE,
                minAgePreference = 24,
                maxAgePreference = 37,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(3L).get(),
                firstName = "Charlie",
                lastName = "Davis",
                dateOfBirth = LocalDate.of(1998, 2, 3),
                gender = Gender.OTHER,
                minAgePreference = 18,
                maxAgePreference = 26,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(4L).get(),
                firstName = "Diana",
                lastName = "Lee",
                dateOfBirth = LocalDate.of(2000, 2, 3),
                gender = Gender.FEMALE,
                minAgePreference = 31,
                maxAgePreference = 38,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(5L).get(),
                firstName = "Eve",
                lastName = "Brown",
                dateOfBirth = LocalDate.of(1995, 8, 19),
                gender = Gender.NOT_IMPORTANT,
                minAgePreference = 40,
                maxAgePreference = 55,
                preferredGender = Gender.NOT_IMPORTANT,
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
        )

        userProfileRepository.saveAll(userProfiles)
    }
}