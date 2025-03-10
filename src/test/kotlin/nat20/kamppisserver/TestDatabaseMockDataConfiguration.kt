package nat20.kamppisserver

import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.*
import nat20.kamppisserver.repository.SwipeRepository
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
    fun databaseInitializer(userRepository: UserRepository, userProfileRepository: UserProfileRepository, swipeRepository: SwipeRepository) = ApplicationRunner {
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableListOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "I'm a passionate traveler who loves exploring new cultures and cuisines. When I'm not studying, you can find me hiking in nature or experimenting with new recipes in the kitchen.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.VANTAA),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.NIGHT_OWL, Lifestyle.PARTY_GOER),
                bio = "I have a deep appreciation for music and often spend my weekends attending live concerts or playing the guitar. My friends describe me as a foodie who loves to explore new restaurants and culinary experiences.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO),
                maxRent = MaxRent.HIGH,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.HOMEBODY, Lifestyle.WORKING),
                bio = "As an avid reader, I enjoy getting lost in a good book and discussing literature with fellow book enthusiasts. Excited to connect with others who share my interests in reading!",
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
                preferredLocations = mutableListOf(City.VANTAA),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.STUDENT, Lifestyle.NIGHT_OWL),
                bio = "As a dedicated student, I balance my time between academics and my love for outdoor activities. I enjoy discovering new hiking trails and capturing beautiful landscapes through photography.",
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
                preferredLocations = mutableListOf(City.ESPOO, City.VANTAA),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableListOf(Lifestyle.PARTY_GOER, Lifestyle.EARLY_BIRD),
                bio = "With a keen interest in fitness and wellness, I start my days with a refreshing morning run. I also enjoy attending local art exhibitions and trying out new coffee shops.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.VANTAA),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.MESSY,
                lifestyle =  mutableListOf(Lifestyle.WORKING, Lifestyle.STUDENT),
                bio = "As a tech enthusiast, I love staying updated with the latest gadgets and innovations. In my free time, I enjoy coding and working on personal projects.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.EARLY_BIRD, Lifestyle.HOMEBODY),
                bio = "I have a passion for volunteering and often spend my weekends helping out at local shelters. I also enjoy practicing yoga and meditation to maintain a balanced lifestyle.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.CASUAL,
                lifestyle =  mutableListOf(Lifestyle.NIGHT_OWL, Lifestyle.WORKING),
                bio = "I have a creative side that I express through painting and DIY crafts. Excited to connect with others who appreciate creativity and the arts!",
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
                preferredLocations = mutableListOf(City.ESPOO),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableListOf(Lifestyle.PARTY_GOER, Lifestyle.STUDENT),
                bio = "I have a deep appreciation for music and often spend my weekends attending live concerts. My friends describe me as a foodie who loves to explore new culinary experiences.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableListOf(Lifestyle.HOMEBODY, Lifestyle.NIGHT_OWL),
                bio = "I'm an animal lover and spend a lot of time with my rescue pets. I also enjoy outdoor activities like camping and kayaking.",
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
                preferredLocations = mutableListOf(City.ESPOO, City.VANTAA),
                maxRent = MaxRent.HIGH,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.STUDENT, Lifestyle.EARLY_BIRD),
                bio = "As a dedicated student, I balance my time between academics and my love for outdoor activities. Discovering new hiking trails and capturing beautiful landscapes through photography are my favorite pastimes.",
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
                preferredLocations = mutableListOf(City.HELSINKI),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.WORKING, Lifestyle.PARTY_GOER),
                bio = "With a background in culinary arts, I love experimenting with new recipes and hosting dinner parties for friends.",
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
                preferredLocations = mutableListOf(City.HELSINKI),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.WORKING, Lifestyle.PARTY_GOER),
                bio = "Music is a big part of my life, and I enjoy playing the guitar and attending live concerts. I'm also a foodie who loves discovering new restaurants and culinary experiences.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO),
                maxRent = MaxRent.HIGH,
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableListOf(Lifestyle.NIGHT_OWL, Lifestyle.HOMEBODY),
                bio = "I'm an avid reader who enjoys diving into mystery novels and discussing them with fellow book lovers. Gardening is another hobby of mine, and I take pride in my flourishing indoor plants.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.VANTAA),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableListOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "I love the nightlife and enjoy attending social events and parties with friends. Dancing and meeting new people are some of my favorite activities.",
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
                preferredLocations = mutableListOf(City.ESPOO, City.VANTAA),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.HOMEBODY, Lifestyle.STUDENT),
                bio = "I enjoy practicing yoga and meditation to maintain a balanced lifestyle. Volunteering at local shelters and giving back to the community is something I find fulfilling.",
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
                preferredLocations = mutableListOf(City.ESPOO, City.VANTAA),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.STUDENT, Lifestyle.WORKING),
                bio = "As a tech enthusiast, I love staying updated with the latest gadgets and innovations. In my free time, I enjoy coding and working on personal projects.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.VANTAA),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableListOf(Lifestyle.WORKING, Lifestyle.EARLY_BIRD),
                bio = "I have a passion for volunteering and often spend my weekends helping out at local shelters. Practicing yoga and meditation helps me maintain a balanced lifestyle.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO),
                maxRent = MaxRent.HIGH,
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableListOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "As a tech enthusiast, I enjoy staying updated with the latest gadgets and working on coding projects. In my free time, I love exploring new coffee shops and trying different brews.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.NIGHT_OWL, Lifestyle.STUDENT),
                bio = "Music is a big part of my life, and I enjoy playing the guitar and attending live concerts. I'm also a foodie who loves discovering new restaurants and culinary experiences.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.VANTAA),
                maxRent = MaxRent.HIGH,
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableListOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "As a sports fan, I love attending live games and cheering for my favorite teams. I also enjoy playing tennis and staying active through various sports.",
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
                preferredLocations = mutableListOf(City.ESPOO, City.VANTAA),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableListOf(Lifestyle.HOMEBODY, Lifestyle.EARLY_BIRD),
                bio = "I'm a film buff who enjoys watching classic movies and discussing them with fellow film enthusiasts. I also have a talent for drawing and often sketch scenes from my favorite films.",
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
                preferredLocations = mutableListOf(City.HELSINKI),
                maxRent = MaxRent.MID,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.STUDENT, Lifestyle.PARTY_GOER),
                bio = "As a science enthusiast, I enjoy reading about the latest discoveries and advancements. I also love trying out new clubs and bars in the city!",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.VANTAA),
                maxRent = MaxRent.HIGH,
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableListOf(Lifestyle.WORKING, Lifestyle.NIGHT_OWL),
                bio = "I have a passion for astronomy and enjoy stargazing and learning about the cosmos. Attending astronomy clubs and events is something I look forward to.",
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
                preferredLocations = mutableListOf(City.VANTAA),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.EARLY_BIRD, Lifestyle.HOMEBODY),
                bio = "I'm a puzzle enthusiast who enjoys solving complex puzzles and brainteasers. I also love playing board games with friends and family during gatherings.",
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
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
                maxRent = MaxRent.LOW,
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableListOf(Lifestyle.NIGHT_OWL, Lifestyle.WORKING),
                bio = "I have a love for fashion and enjoy keeping up with the latest trends and styles. Designing my own clothes and accessories is a creative outlet for me.",
                updatedAt = null,
            )
        )

        // Save all mock user profiles to the database
        userProfileRepository.saveAll(userProfiles)

        val swipes = listOf(
            Swipe(userRepository.findById(1L).get(), userRepository.findById(2L).get(), true),
            Swipe(userRepository.findById(1L).get(), userRepository.findById(3L).get(), true),
            Swipe(userRepository.findById(2L).get(), userRepository.findById(1L).get(), true),
            Swipe(userRepository.findById(3L).get(), userRepository.findById(1L).get(), true)
        )

        swipeRepository.saveAll(swipes)
    }
}