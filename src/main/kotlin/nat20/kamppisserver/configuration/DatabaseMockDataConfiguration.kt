package nat20.kamppisserver.configuration

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.*
import nat20.kamppisserver.repository.*
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.LocalDate

@Configuration
@Profile("dev", "prod") // This config will only load when the dev or prod profile is active from application.properties
class DatabaseMockDataConfiguration {

    /**
     * Database initializer for adding mock data into the database
     */
    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            userProfileRepository: UserProfileRepository,
                            profilePhotoRepository: ProfilePhotoRepository,
                            matchRepository: MatchRepository, swipeRepository: SwipeRepository,
                            flatRepository: FlatRepository, profileRepository: ProfileRepository,
                            roomProfileRepository: RoomProfileRepository
    ) = ApplicationRunner {

        // Check if users table has any entries
        if (userRepository.count() > 0) {
            println("Database already initialized, skipping mock data insertion.")
            return@ApplicationRunner
        }

        println("Initializing database with mock data...")

        val users = listOf(
            User(
                email = "alice.smith@example.com",
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 5, 14), // Age 34
                gender = Gender.FEMALE
            ),
            User(
                email = "bob.johnson@example.com",
                firstName = "Bob",
                lastName = "Johnson",
                dateOfBirth = LocalDate.of(1985, 11, 22), // Age 39
                gender = Gender.MALE
            ),
            User(
                email = "charlie.davis@example.com",
                firstName = "Charlie",
                lastName = "Davis",
                dateOfBirth = LocalDate.of(1998, 2, 3), // Age 27
                gender = Gender.OTHER
            ),
            User(
                email = "diana.lee@example.com",
                firstName = "Diana",
                lastName = "Lee",
                dateOfBirth = LocalDate.of(2000, 2, 3), // Age 25
                gender = Gender.FEMALE
            ),
            User(
                email = "eve.brown@example.com",
                firstName = "Eve",
                lastName = "Brown",
                dateOfBirth = LocalDate.of(1995, 8, 19), // Age 29
                gender = Gender.NOT_IMPORTANT
            ),
            User(
                email = "frank.miller@example.com",
                firstName = "Frank",
                lastName = "Miller",
                dateOfBirth = LocalDate.of(1988, 3, 22), // Age 36
                gender = Gender.MALE
            ),
            User(
                email = "grace.wilson@example.com",
                firstName = "Grace",
                lastName = "Wilson",
                dateOfBirth = LocalDate.of(1992, 7, 15), // Age 32
                gender = Gender.FEMALE
            ),
            User(
                email = "harry.moore@example.com",
                firstName = "Harry",
                lastName = "Moore",
                dateOfBirth = LocalDate.of(1985, 1, 10), // Age 40
                gender = Gender.MALE
            ),
            User(
                email = "isabella.taylor@example.com",
                firstName = "Isabella",
                lastName = "Taylor",
                dateOfBirth = LocalDate.of(1999, 12, 5), // Age 25
                gender = Gender.FEMALE
            ),
            User(
                email = "jack.anderson@example.com",
                firstName = "Jack",
                lastName = "Anderson",
                dateOfBirth = LocalDate.of(1990, 6, 30), // Age 34
                gender = Gender.MALE
            ),
            User(
                email = "karen.thomas@example.com",
                firstName = "Karen",
                lastName = "Thomas",
                dateOfBirth = LocalDate.of(1982, 8, 20), // Age 42
                gender = Gender.FEMALE
            ),
            User(
                email = "luke.jackson@example.com",
                firstName = "Luke",
                lastName = "Jackson",
                dateOfBirth = LocalDate.of(1995, 4, 2), // Age 29
                gender = Gender.MALE
            ),
            User(
                email = "mia.white@example.com",
                firstName = "Mia",
                lastName = "White",
                dateOfBirth = LocalDate.of(2000, 2, 17), // Age 25
                gender = Gender.FEMALE
            ),
            User(
                email = "nathan.harris@example.com",
                firstName = "Nathan",
                lastName = "Harris",
                dateOfBirth = LocalDate.of(1987, 9, 9), // Age 37
                gender = Gender.MALE
            ),
            User(
                email = "olivia.martin@example.com",
                firstName = "Olivia",
                lastName = "Martin",
                dateOfBirth = LocalDate.of(1993, 11, 11), // Age 31
                gender = Gender.FEMALE
            ),
            User(
                email = "peter.thompson@example.com",
                firstName = "Peter",
                lastName = "Thompson",
                dateOfBirth = LocalDate.of(1980, 5, 5), // Age 44
                gender = Gender.MALE
            ),
            User(
                email = "quinn.garcia@example.com",
                firstName = "Quinn",
                lastName = "Garcia",
                dateOfBirth = LocalDate.of(1998, 3, 3), // Age 26
                gender = Gender.OTHER
            ),
            User(
                email = "rachel.martinez@example.com",
                firstName = "Rachel",
                lastName = "Martinez",
                dateOfBirth = LocalDate.of(1989, 10, 25), // Age 35
                gender = Gender.FEMALE
            ),
            User(
                email = "samuel.robinson@example.com",
                firstName = "Samuel",
                lastName = "Robinson",
                dateOfBirth = LocalDate.of(1978, 12, 1), // Age 46
                gender = Gender.MALE
            ),
            User(
                email = "tina.clark@example.com",
                firstName = "Tina",
                lastName = "Clark",
                dateOfBirth = LocalDate.of(1996, 7, 19), // Age 28
                gender = Gender.FEMALE
            ),
            User(
                email = "ursula.rodriguez@example.com",
                firstName = "Ursula",
                lastName = "Rodriguez",
                dateOfBirth = LocalDate.of(1991, 1, 29), // Age 34
                gender = Gender.FEMALE
            ),
            User(
                email = "victor.lewis@example.com",
                firstName = "Victor",
                lastName = "Lewis",
                dateOfBirth = LocalDate.of(1984, 4, 14), // Age 40
                gender = Gender.MALE
            ),
            User(
                email = "wendy.walker@example.com",
                firstName = "Wendy",
                lastName = "Walker",
                dateOfBirth = LocalDate.of(1994, 6, 8), // Age 30
                gender = Gender.FEMALE
            ),
            User(
                email = "xavier.hall@example.com",
                firstName = "Xavier",
                lastName = "Hall",
                dateOfBirth = LocalDate.of(1986, 9, 30), // Age 38
                gender = Gender.MALE
            ),
            User(
                email = "yvonne.allen@example.com",
                firstName = "Yvonne",
                lastName = "Allen",
                dateOfBirth = LocalDate.of(1997, 2, 22), // Age 27
                gender = Gender.FEMALE
            ),
            User(
                email = "zachary.young@example.com",
                firstName = "Zachary",
                lastName = "Young",
                dateOfBirth = LocalDate.of(1983, 11, 12), // Age 41
                gender = Gender.MALE
            )
        )

        // Save all mock users to the database
        userRepository.saveAll(users)
/*
        // NOTE! Ages are calculated on 2025-2-21
        val userProfiles = listOf(
            UserProfile(
                user = userRepository.findById(1L).get(),
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 5, 14), // Age 34
                gender = Gender.FEMALE,
                minAgePreference = 20,
                maxAgePreference = 29,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
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
                minAgePreference = 22,
                maxAgePreference = 39,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
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
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.OTHER, Gender.FEMALE),
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
                minAgePreference = 29,
                maxAgePreference = 40,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                preferredLocations = mutableListOf(City.ESPOO, City.VANTAA),
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
                minAgePreference = 38,
                maxAgePreference = 57,
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
                minAgePreference = 20,
                maxAgePreference = 33,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
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
                minAgePreference = 23,
                maxAgePreference = 32,
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
                minAgePreference = 18,
                maxAgePreference = 28,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
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
                minAgePreference = 21,
                maxAgePreference = 30,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                preferredLocations = mutableListOf(City.ESPOO, City.VANTAA),
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
                minAgePreference = 22,
                maxAgePreference = 27,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
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
                minAgePreference = 24,
                maxAgePreference = 34,
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
                minAgePreference = 19,
                maxAgePreference = 31,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO),
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
                minAgePreference = 18,
                maxAgePreference = 33,
                preferredGenders = mutableListOf(Gender.MALE, Gender.FEMALE),
                preferredLocations = mutableListOf(City.HELSINKI, City.VANTAA),
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
                minAgePreference = 20,
                maxAgePreference = 35,
                preferredGenders = mutableListOf(Gender.MALE, Gender.FEMALE),
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
                minAgePreference = 21,
                maxAgePreference = 33,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
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
                minAgePreference = 23,
                maxAgePreference = 36,
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
                minAgePreference = 18,
                maxAgePreference = 29,
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
                minAgePreference = 19,
                maxAgePreference = 31,
                preferredGenders = mutableListOf(Gender.MALE, Gender.FEMALE),
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
                minAgePreference = 24,
                maxAgePreference = 38,
                preferredGenders = mutableListOf(Gender.MALE, Gender.OTHER),
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
                minAgePreference = 20,
                maxAgePreference = 33,
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
                minAgePreference = 21,
                maxAgePreference = 35,
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
                minAgePreference = 20,
                maxAgePreference = 36,
                preferredGenders = mutableListOf(Gender.MALE, Gender.FEMALE),
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
                minAgePreference = 21,
                maxAgePreference = 33,
                preferredGenders = mutableListOf(Gender.NOT_IMPORTANT),
                preferredLocations = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
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
                minAgePreference = 19,
                maxAgePreference = 36,
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
                minAgePreference = 20,
                maxAgePreference = 33,
                preferredGenders = mutableListOf(Gender.FEMALE, Gender.OTHER),
                preferredLocations = mutableListOf(City.VANTAA, City.ESPOO),
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
                minAgePreference = 21,
                maxAgePreference = 32,
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

        val userPhotos = listOf(
            UserPhoto(
                userProfile = userProfileRepository.findById(1L).get(),
                name = "https://cdn.stocksnap.io/img-thumbs/960w/woman-portrait_CLTJPNEBUL.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(1L).get(),
                name = "https://images.pexels.com/photos/7508810/pexels-photo-7508810.jpeg",
                isProfilePhoto = false,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(1L).get(),
                name = "https://images.pexels.com/photos/7994283/pexels-photo-7994283.jpeg",
                isProfilePhoto = false,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(2L).get(),
                name = "https://freerangestock.com/sample/169954/young-man-in-contemplative-urban-scene.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(2L).get(),
                name = "https://images.pexels.com/photos/7252531/pexels-photo-7252531.jpeg",
                isProfilePhoto = false,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(3L).get(),
                name = "https://cdn.stocksnap.io/img-thumbs/960w/business-man_IVZBYWKEFM.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(3L).get(),
                name = "https://images.pexels.com/photos/1472856/pexels-photo-1472856.jpeg",
                isProfilePhoto = false,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(4L).get(),
                name = "https://upload.wikimedia.org/wikipedia/commons/2/2d/African-Woman-Business-Woman-Young-Woman-Black-Woman-3439224.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(4L).get(),
                name = "https://images.pexels.com/photos/6550399/pexels-photo-6550399.jpeg",
                isProfilePhoto = false,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(5L).get(),
                name = "https://live.staticflickr.com/2727/4523649809_f893abca83_b.jpg",
                isProfilePhoto = true,
            ),
            UserPhoto(
                userProfile = userProfileRepository.findById(5L).get(),
                name = "https://images.pexels.com/photos/7849189/pexels-photo-7849189.jpeg",
                isProfilePhoto = false,
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
        userPhotoRepository.saveAll(userPhotos)*/

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

        val swipes = listOf(
            Swipe(userRepository.findById(1L).get(), userRepository.findById(2L).get(), true),
            Swipe(userRepository.findById(1L).get(), userRepository.findById(3L).get(), true),
            Swipe(userRepository.findById(2L).get(), userRepository.findById(1L).get(), true),
            Swipe(userRepository.findById(3L).get(), userRepository.findById(1L).get(), true)
        )

        swipeRepository.saveAll(swipes)

        val flats = listOf(
            Flat(
                "HOAS kolme huonetta",
                "Kolme huonetta ja keittiökomero Kalasatamassa. Merellinen viima asunnossa ja metron urbaani äänimaisema luovat ison kaupungin henkeä!",
                City.HELSINKI,
                3,
                mutableListOf(Utilities.WIFI, Utilities.BALCONY),
            ),
            Flat(
                "Hippikommuuni",
                "Kolme hippiä etsii neljättä. Olisitko se sinä? Woodstock 69' iltamat joka perjantai. Mukana mutaliukumäki.",
                City.ESPOO,
                4,
                mutableListOf(Utilities.SEPARATE_BATHROOM_AND_SHOWER, Utilities.LAUNDRY_MACHINE),
            )
        )

        flatRepository.saveAll(flats)

        val roomProfiles = listOf(
            RoomProfile(
                mutableListOf(userRepository.findById(1L).get()),
                flatRepository.findById(1L).get(),
                450,
                true,
                bio = "Kokonaiset 9 neliötä, ikkunoissa kalterit. Pehmustetut seinät.",
                ),
            RoomProfile(mutableListOf(userRepository.findById(1L).get()),
                flatRepository.findById(1L).get(),
                600,
                true,
                bio = "20 neliötä merinäköalalla. Avara näkymä. (Ei sisällä seiniä tai kattoa)."),
            RoomProfile(mutableListOf(userRepository.findById(11L).get(), userRepository.findById(12L).get(), userRepository.findById(13L).get()),
                flatRepository.findById(2L).get(),
                250,
                true,
                bio = "Oma huone ja perunamaa."
                )
        )

        profileRepository.saveAll(roomProfiles)

        println("Mock data inserted successfully!")

    }
}