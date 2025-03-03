package nat20.kamppisserver

import nat20.kamppisserver.domain.City
import nat20.kamppisserver.domain.Gender
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
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
            ),
            User(
                email = "frank.miller@example.com",
            ),
            User(
                email = "grace.wilson@example.com",
            ),
            User(
                email = "harry.moore@example.com",
            ),
            User(
                email = "isabella.taylor@example.com",
            ),
            User(
                email = "jack.anderson@example.com",
            ),
            User(
                email = "karen.thomas@example.com",
            ),
            User(
                email = "luke.jackson@example.com",
            ),
            User(
                email = "mia.white@example.com",
            ),
            User(
                email = "nathan.harris@example.com",
            ),
            User(
                email = "olivia.martin@example.com",
            ),
            User(
                email = "peter.thompson@example.com",
            ),
            User(
                email = "quinn.garcia@example.com",
            ),
            User(
                email = "rachel.martinez@example.com",
            ),
            User(
                email = "samuel.robinson@example.com",
            ),
            User(
                email = "tina.clark@example.com",
            ),
            User(
                email = "ursula.rodriguez@example.com",
            ),
            User(
                email = "victor.lewis@example.com",
            ),
            User(
                email = "wendy.walker@example.com",
            ),
            User(
                email = "xavier.hall@example.com",
            ),
            User(
                email = "yvonne.allen@example.com",
            ),
            User(
                email = "zachary.young@example.com",
            )
        )

        // Save all mock users to the database
        userRepository.saveAll(users)

        // NOTE! Ages are calculated on 2025-2-21
        val userProfiles = listOf(
            UserProfile(
                user = userRepository.findById(1L).get(),
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 5, 14), // Age 34
                gender = Gender.FEMALE,
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = mutableListOf(City.HELSINKI, City.ESPOO),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(2L).get(),
                firstName = "Bob",
                lastName = "Johnson",
                dateOfBirth = LocalDate.of(1985, 11, 22), // Age 39
                gender = Gender.MALE,
                minAgePreference = 24,
                maxAgePreference = 37,
                preferredGenders = mutableListOf(Gender.MALE, Gender.FEMALE),
                locations = mutableListOf(City.HELSINKI, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(3L).get(),
                firstName = "Charlie",
                lastName = "Davis",
                dateOfBirth = LocalDate.of(1998, 2, 3), // Age 27
                gender = Gender.OTHER,
                minAgePreference = 18,
                maxAgePreference = 26,
                preferredGenders = mutableListOf(Gender.OTHER),
                locations = mutableListOf(City.HELSINKI, City.ESPOO),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(4L).get(),
                firstName = "Diana",
                lastName = "Lee",
                dateOfBirth = LocalDate.of(2000, 2, 3), // Age 25
                gender = Gender.FEMALE,
                minAgePreference = 31,
                maxAgePreference = 38,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = mutableListOf(City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(5L).get(),
                firstName = "Eve",
                lastName = "Brown",
                dateOfBirth = LocalDate.of(1995, 8, 19), // Age 29
                gender = Gender.NOT_IMPORTANT,
                minAgePreference = 40,
                maxAgePreference = 55,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = mutableListOf(City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(6L).get(),
                firstName = "Frank",
                lastName = "Miller",
                dateOfBirth = LocalDate.of(1988, 3, 22), // Age 36
                gender = Gender.MALE,
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = mutableListOf(City.HELSINKI, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(7L).get(),
                firstName = "Grace",
                lastName = "Wilson",
                dateOfBirth = LocalDate.of(1992, 7, 15), // Age 32
                gender = Gender.FEMALE,
                minAgePreference = 25,
                maxAgePreference = 30,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(8L).get(),
                firstName = "Harry",
                lastName = "Moore",
                dateOfBirth = LocalDate.of(1985, 1, 10), // Age 40
                gender = Gender.MALE,
                minAgePreference = 20,
                maxAgePreference = 26,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = mutableListOf(City.HELSINKI, City.ESPOO),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(9L).get(),
                firstName = "Isabella",
                lastName = "Taylor",
                dateOfBirth = LocalDate.of(1999, 12, 5), // Age 25
                gender = Gender.FEMALE,
                minAgePreference = 23,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = mutableListOf(City.ESPOO),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(10L).get(),
                firstName = "Jack",
                lastName = "Anderson",
                dateOfBirth = LocalDate.of(1990, 6, 30), // Age 34
                gender = Gender.MALE,
                minAgePreference = 24,
                maxAgePreference = 29,
                preferredGenders = mutableListOf(Gender.MALE, Gender.OTHER),
                locations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(11L).get(),
                firstName = "Karen",
                lastName = "Thomas",
                dateOfBirth = LocalDate.of(1982, 8, 20), // Age 42
                gender = Gender.FEMALE,
                minAgePreference = 26,
                maxAgePreference = 32,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = mutableListOf(City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(12L).get(),
                firstName = "Luke",
                lastName = "Jackson",
                dateOfBirth = LocalDate.of(1995, 4, 2), // Age 29
                gender = Gender.MALE,
                minAgePreference = 21,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = mutableListOf(City.HELSINKI),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(13L).get(),
                firstName = "Mia",
                lastName = "White",
                dateOfBirth = LocalDate.of(2000, 2, 17), // Age 25
                gender = Gender.FEMALE,
                minAgePreference = 19,
                maxAgePreference = 24,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = mutableListOf(City.HELSINKI),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(14L).get(),
                firstName = "Nathan",
                lastName = "Harris",
                dateOfBirth = LocalDate.of(1987, 9, 9), // Age 37
                gender = Gender.MALE,
                minAgePreference = 22,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = mutableListOf(City.HELSINKI, City.ESPOO),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(15L).get(),
                firstName = "Olivia",
                lastName = "Martin",
                dateOfBirth = LocalDate.of(1993, 11, 11), // Age 31
                gender = Gender.FEMALE,
                minAgePreference = 23,
                maxAgePreference = 29,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = mutableListOf(City.HELSINKI, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(16L).get(),
                firstName = "Peter",
                lastName = "Thompson",
                dateOfBirth = LocalDate.of(1980, 5, 5), // Age 44
                gender = Gender.MALE,
                minAgePreference = 25,
                maxAgePreference = 30,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = mutableListOf(City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(17L).get(),
                firstName = "Quinn",
                lastName = "Garcia",
                dateOfBirth = LocalDate.of(1998, 3, 3), // Age 26
                gender = Gender.OTHER,
                minAgePreference = 20,
                maxAgePreference = 25,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locations = mutableListOf(City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(18L).get(),
                firstName = "Rachel",
                lastName = "Martinez",
                dateOfBirth = LocalDate.of(1989, 10, 25), // Age 35
                gender = Gender.FEMALE,
                minAgePreference = 21,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = mutableListOf(City.HELSINKI, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(19L).get(),
                firstName = "Samuel",
                lastName = "Robinson",
                dateOfBirth = LocalDate.of(1978, 12, 1), // Age 46
                gender = Gender.MALE,
                minAgePreference = 26,
                maxAgePreference = 33,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = mutableListOf(City.HELSINKI, City.ESPOO),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(20L).get(),
                firstName = "Tina",
                lastName = "Clark",
                dateOfBirth = LocalDate.of(1996, 7, 19), // Age 28
                gender = Gender.FEMALE,
                minAgePreference = 22,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(21L).get(),
                firstName = "Ursula",
                lastName = "Rodriguez",
                dateOfBirth = LocalDate.of(1991, 1, 29), // Age 34
                gender = Gender.FEMALE,
                minAgePreference = 24,
                maxAgePreference = 30,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = mutableListOf(City.HELSINKI, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(22L).get(),
                firstName = "Victor",
                lastName = "Lewis",
                dateOfBirth = LocalDate.of(1984, 4, 14), // Age 40
                gender = Gender.MALE,
                minAgePreference = 25,
                maxAgePreference = 31,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = mutableListOf(City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(23L).get(),
                firstName = "Wendy",
                lastName = "Walker",
                dateOfBirth = LocalDate.of(1994, 6, 8), // Age 30
                gender = Gender.FEMALE,
                minAgePreference = 23,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = mutableListOf(City.HELSINKI),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(24L).get(),
                firstName = "Xavier",
                lastName = "Hall",
                dateOfBirth = LocalDate.of(1986, 9, 30), // Age 38
                gender = Gender.MALE,
                minAgePreference = 20,
                maxAgePreference = 26,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = mutableListOf(City.HELSINKI, City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(25L).get(),
                firstName = "Yvonne",
                lastName = "Allen",
                dateOfBirth = LocalDate.of(1997, 2, 22), // Age 27
                gender = Gender.FEMALE,
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locations = mutableListOf(City.VANTAA),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(26L).get(),
                firstName = "Zachary",
                lastName = "Young",
                dateOfBirth = LocalDate.of(1983, 11, 12), // Age 41
                gender = Gender.MALE,
                minAgePreference = 24,
                maxAgePreference = 29,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
                bio = null,
                updatedAt = null,
            )
        )

        // Save all mock user profiles to the database
        userProfileRepository.saveAll(userProfiles)
    }
}