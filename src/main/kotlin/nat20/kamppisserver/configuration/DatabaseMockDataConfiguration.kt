package nat20.kamppisserver.configuration

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.UserPhotoRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.LocalDate

@Configuration
@Profile("dev") // This config will only load when the dev profile is active from application.properties
class DatabaseMockDataConfiguration {

    /**
     * Database initializer for adding mock data into the database
     */
    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            userProfileRepository: UserProfileRepository,
                            userPhotoRepository: UserPhotoRepository,
                            matchRepository: MatchRepository
    ) = ApplicationRunner {

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

        val userProfiles = listOf(
            UserProfile(
                user = userRepository.findById(1L).get(),
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 5, 14),
                gender = Gender.FEMALE,
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.FEMALE),
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
                preferredGenders = mutableListOf(Gender.MALE, Gender.FEMALE),
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
                preferredGenders = mutableListOf(Gender.OTHER),
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
                preferredGenders = mutableListOf(Gender.FEMALE),
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
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(6L).get(),
                firstName = "Frank",
                lastName = "Miller",
                dateOfBirth = LocalDate.of(1988, 3, 22),
                gender = Gender.MALE,
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(7L).get(),
                firstName = "Grace",
                lastName = "Wilson",
                dateOfBirth = LocalDate.of(1992, 7, 15),
                gender = Gender.FEMALE,
                minAgePreference = 25,
                maxAgePreference = 30,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(8L).get(),
                firstName = "Harry",
                lastName = "Moore",
                dateOfBirth = LocalDate.of(1985, 1, 10),
                gender = Gender.MALE,
                minAgePreference = 20,
                maxAgePreference = 26,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(9L).get(),
                firstName = "Isabella",
                lastName = "Taylor",
                dateOfBirth = LocalDate.of(1999, 12, 5),
                gender = Gender.FEMALE,
                minAgePreference = 23,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(10L).get(),
                firstName = "Jack",
                lastName = "Anderson",
                dateOfBirth = LocalDate.of(1990, 6, 30),
                gender = Gender.MALE,
                minAgePreference = 24,
                maxAgePreference = 29,
                preferredGenders = mutableListOf(Gender.MALE, Gender.OTHER),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(11L).get(),
                firstName = "Karen",
                lastName = "Thomas",
                dateOfBirth = LocalDate.of(1982, 8, 20),
                gender = Gender.FEMALE,
                minAgePreference = 26,
                maxAgePreference = 32,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(12L).get(),
                firstName = "Luke",
                lastName = "Jackson",
                dateOfBirth = LocalDate.of(1995, 4, 2),
                gender = Gender.MALE,
                minAgePreference = 21,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(13L).get(),
                firstName = "Mia",
                lastName = "White",
                dateOfBirth = LocalDate.of(2000, 2, 17),
                gender = Gender.FEMALE,
                minAgePreference = 19,
                maxAgePreference = 24,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(14L).get(),
                firstName = "Nathan",
                lastName = "Harris",
                dateOfBirth = LocalDate.of(1987, 9, 9),
                gender = Gender.MALE,
                minAgePreference = 22,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(15L).get(),
                firstName = "Olivia",
                lastName = "Martin",
                dateOfBirth = LocalDate.of(1993, 11, 11),
                gender = Gender.FEMALE,
                minAgePreference = 23,
                maxAgePreference = 29,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(16L).get(),
                firstName = "Peter",
                lastName = "Thompson",
                dateOfBirth = LocalDate.of(1980, 5, 5),
                gender = Gender.MALE,
                minAgePreference = 25,
                maxAgePreference = 30,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(17L).get(),
                firstName = "Quinn",
                lastName = "Garcia",
                dateOfBirth = LocalDate.of(1998, 3, 3),
                gender = Gender.OTHER,
                minAgePreference = 20,
                maxAgePreference = 25,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(18L).get(),
                firstName = "Rachel",
                lastName = "Martinez",
                dateOfBirth = LocalDate.of(1989, 10, 25),
                gender = Gender.FEMALE,
                minAgePreference = 21,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(19L).get(),
                firstName = "Samuel",
                lastName = "Robinson",
                dateOfBirth = LocalDate.of(1978, 12, 1),
                gender = Gender.MALE,
                minAgePreference = 26,
                maxAgePreference = 33,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(20L).get(),
                firstName = "Tina",
                lastName = "Clark",
                dateOfBirth = LocalDate.of(1996, 7, 19),
                gender = Gender.FEMALE,
                minAgePreference = 22,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(21L).get(),
                firstName = "Ursula",
                lastName = "Rodriguez",
                dateOfBirth = LocalDate.of(1991, 1, 29),
                gender = Gender.FEMALE,
                minAgePreference = 24,
                maxAgePreference = 30,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(22L).get(),
                firstName = "Victor",
                lastName = "Lewis",
                dateOfBirth = LocalDate.of(1984, 4, 14),
                gender = Gender.MALE,
                minAgePreference = 25,
                maxAgePreference = 31,
                preferredGenders = mutableListOf(Gender.MALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(23L).get(),
                firstName = "Wendy",
                lastName = "Walker",
                dateOfBirth = LocalDate.of(1994, 6, 8),
                gender = Gender.FEMALE,
                minAgePreference = 23,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.FEMALE),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(24L).get(),
                firstName = "Xavier",
                lastName = "Hall",
                dateOfBirth = LocalDate.of(1986, 9, 30),
                gender = Gender.MALE,
                minAgePreference = 20,
                maxAgePreference = 26,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(25L).get(),
                firstName = "Yvonne",
                lastName = "Allen",
                dateOfBirth = LocalDate.of(1997, 2, 22),
                gender = Gender.FEMALE,
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            ),
            UserProfile(
                user = userRepository.findById(26L).get(),
                firstName = "Zachary",
                lastName = "Young",
                dateOfBirth = LocalDate.of(1983, 11, 12),
                gender = Gender.MALE,
                minAgePreference = 24,
                maxAgePreference = 29,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                locations = listOf(),
                bio = null,
                updatedAt = null,
            )
        )

        // Save all mock user profiles to the database
        userProfileRepository.saveAll(userProfiles)

        val userPhotos = listOf(
            UserPhoto(
                userProfile = userProfileRepository.findById(1L).get(),
                name = "https://cdn.stocksnap.io/img-thumbs/960w/woman-portrait_CLTJPNEBUL.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(2L).get(),
                name = "https://freerangestock.com/sample/169954/young-man-in-contemplative-urban-scene.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(3L).get(),
                name = "https://cdn.stocksnap.io/img-thumbs/960w/business-man_IVZBYWKEFM.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(4L).get(),
                name = "https://upload.wikimedia.org/wikipedia/commons/2/2d/African-Woman-Business-Woman-Young-Woman-Black-Woman-3439224.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(5L).get(),
                name = "https://live.staticflickr.com/2727/4523649809_f893abca83_b.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(6L).get(),
                name = "https://images.pexels.com/photos/6274712/pexels-photo-6274712.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(7L).get(),
                name = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(8L).get(),
                name = "https://images.pexels.com/photos/6274712/pexels-photo-6274712.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(9L).get(),
                name = "https://images.pexels.com/photos/7275385/pexels-photo-7275385.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(10L).get(),
                name = "https://images.pexels.com/photos/4307869/pexels-photo-4307869.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(11L).get(),
                name = "https://images.pexels.com/photos/5393594/pexels-photo-5393594.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(12L).get(),
                name = "https://images.pexels.com/photos/5384445/pexels-photo-5384445.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(13L).get(),
                name = "https://images.pexels.com/photos/6976943/pexels-photo-6976943.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(14L).get(),
                name = "https://images.pexels.com/photos/5490276/pexels-photo-5490276.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(15L).get(),
                name = "https://images.pexels.com/photos/8420889/pexels-photo-8420889.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(16L).get(),
                name = "https://images.pexels.com/photos/8090137/pexels-photo-8090137.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(17L).get(),
                name = "https://images.pexels.com/photos/3796217/pexels-photo-3796217.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(18L).get(),
                name = "https://images.pexels.com/photos/5876695/pexels-photo-5876695.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(19L).get(),
                name = "https://images.pexels.com/photos/7745573/pexels-photo-7745573.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(20L).get(),
                name = "https://images.pexels.com/photos/6000065/pexels-photo-6000065.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(21L).get(),
                name = "https://images.pexels.com/photos/3586091/pexels-photo-3586091.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(22L).get(),
                name = "https://images.pexels.com/photos/7116213/pexels-photo-7116213.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(23L).get(),
                name = "https://images.pexels.com/photos/6608313/pexels-photo-6608313.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(24L).get(),
                name = "https://images.pexels.com/photos/2589653/pexels-photo-2589653.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(25L).get(),
                name = "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(26L).get(),
                name = "https://images.pexels.com/photos/1040881/pexels-photo-1040881.jpeg",
                isProfilePhoto = true,
            ),
        )

        // Save photos to database
        userPhotoRepository.saveAll(userPhotos)

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