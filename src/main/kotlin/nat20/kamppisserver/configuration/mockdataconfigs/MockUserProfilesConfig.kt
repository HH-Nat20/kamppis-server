package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.Cleanliness
import nat20.kamppisserver.domain.enums.Lifestyle
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.repository.ProfileRepository

class MockUserProfilesConfig {

    /**
     * Database initializer for adding mock user profile data into the database
     */
    fun insertMockUserProfilesToDatabase(
        userRepository: UserRepository,
        profileRepository: ProfileRepository
    ) {
        // These 26 users are looking for another roommate to look for a flat together
        val userProfiles1 = listOf(
            UserProfile(
                user = userRepository.findById(1L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "I'm a passionate traveler who loves exploring new cultures and cuisines. When I'm not studying, you can find me hiking in nature or experimenting with new recipes in the kitchen."
            ),
            UserProfile(
                user = userRepository.findById(2L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.PARTY_GOER),
                bio =
                    "I have a deep appreciation for music and often spend my weekends attending live concerts or playing the guitar. My friends describe me as a foodie who loves to explore new restaurants and culinary experiences."
                ),
            UserProfile(
                user = userRepository.findById(3L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.WORKING),
                bio = "As an avid reader, I enjoy getting lost in a good book and discussing literature with fellow book enthusiasts. Excited to connect with others who share my interests in reading!"
            ),
            UserProfile(
                user = userRepository.findById(4L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.STUDENT, Lifestyle.NIGHT_OWL),
                bio = "As a dedicated student, I balance my time between academics and my love for outdoor activities. I enjoy discovering new hiking trails and capturing beautiful landscapes through photography."
            ),
            UserProfile(
                user = userRepository.findById(5L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.EARLY_BIRD),
                bio = "With a keen interest in fitness and wellness, I start my days with a refreshing morning run. I also enjoy attending local art exhibitions and trying out new coffee shops."
            ),
            UserProfile(
                user = userRepository.findById(6L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle =  mutableSetOf(Lifestyle.WORKING, Lifestyle.STUDENT),
                bio = "As a tech enthusiast, I love staying updated with the latest gadgets and innovations. In my free time, I enjoy coding and working on personal projects."
            ),
            UserProfile(
                user = userRepository.findById(7L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.HOMEBODY),
                bio = "I have a passion for volunteering and often spend my weekends helping out at local shelters. I also enjoy practicing yoga and meditation to maintain a balanced lifestyle."
            ),
            UserProfile(
                user = userRepository.findById(8L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle =  mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.WORKING),
                bio = "I have a creative side that I express through painting and DIY crafts. Excited to connect with others who appreciate creativity and the arts!"
            ),
            UserProfile(
                user = userRepository.findById(9L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.STUDENT),
                bio = "I have a deep appreciation for music and often spend my weekends attending live concerts. My friends describe me as a foodie who loves to explore new culinary experiences."
            ),
            UserProfile(
                user = userRepository.findById(10L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.NIGHT_OWL),
                bio = "I'm an animal lover and spend a lot of time with my rescue pets. I also enjoy outdoor activities like camping and kayaking."
            ),
            UserProfile(
                user = userRepository.findById(11L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.STUDENT, Lifestyle.EARLY_BIRD),
                bio = "As a dedicated student, I balance my time between academics and my love for outdoor activities. Discovering new hiking trails and capturing beautiful landscapes through photography are my favorite pastimes."
            ),
            UserProfile(
                user = userRepository.findById(12L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.WORKING, Lifestyle.PARTY_GOER),
                bio = "With a background in culinary arts, I love experimenting with new recipes and hosting dinner parties for friends."
            ),
            UserProfile(
                user = userRepository.findById(13L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.WORKING, Lifestyle.PARTY_GOER),
                bio = "Music is a big part of my life, and I enjoy playing the guitar and attending live concerts. I'm also a foodie who loves discovering new restaurants and culinary experiences."
            ),
            UserProfile(
                user = userRepository.findById(14L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.HOMEBODY),
                bio = "I'm an avid reader who enjoys diving into mystery novels and discussing them with fellow book lovers. Gardening is another hobby of mine, and I take pride in my flourishing indoor plants."
            ),
            UserProfile(
                user = userRepository.findById(15L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "I love the nightlife and enjoy attending social events and parties with friends. Dancing and meeting new people are some of my favorite activities."
            ),
            UserProfile(
                user = userRepository.findById(16L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.STUDENT),
                bio = "I enjoy practicing yoga and meditation to maintain a balanced lifestyle. Volunteering at local shelters and giving back to the community is something I find fulfilling."
            ),
            UserProfile(
                user = userRepository.findById(17L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.STUDENT, Lifestyle.WORKING),
                bio = "As a tech enthusiast, I love staying updated with the latest gadgets and innovations. In my free time, I enjoy coding and working on personal projects."
            ),
            UserProfile(
                user = userRepository.findById(18L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.WORKING, Lifestyle.EARLY_BIRD),
                bio = "I have a passion for volunteering and often spend my weekends helping out at local shelters. Practicing yoga and meditation helps me maintain a balanced lifestyle."
            ),
            UserProfile(
                user = userRepository.findById(19L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "As a tech enthusiast, I enjoy staying updated with the latest gadgets and working on coding projects. In my free time, I love exploring new coffee shops and trying different brews."
            ),
            UserProfile(
                user = userRepository.findById(20L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.STUDENT),
                bio = "Music is a big part of my life, and I enjoy playing the guitar and attending live concerts. I'm also a foodie who loves discovering new restaurants and culinary experiences."
            ),
            UserProfile(
                user = userRepository.findById(21L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "As a sports fan, I love attending live games and cheering for my favorite teams. I also enjoy playing tennis and staying active through various sports."
            ),
            UserProfile(
                user = userRepository.findById(22L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.EARLY_BIRD),
                bio = "I'm a film buff who enjoys watching classic movies and discussing them with fellow film enthusiasts. I also have a talent for drawing and often sketch scenes from my favorite films."
            ),
            UserProfile(
                user = userRepository.findById(23L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.STUDENT, Lifestyle.PARTY_GOER),
                bio = "As a science enthusiast, I enjoy reading about the latest discoveries and advancements. I also love trying out new clubs and bars in the city!"
            ),
            UserProfile(
                user = userRepository.findById(24L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.WORKING, Lifestyle.NIGHT_OWL),
                bio = "I have a passion for astronomy and enjoy stargazing and learning about the cosmos. Attending astronomy clubs and events is something I look forward to."
            ),
            UserProfile(
                user = userRepository.findById(25L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.HOMEBODY),
                bio = "I'm a puzzle enthusiast who enjoys solving complex puzzles and brainteasers. I also love playing board games with friends and family during gatherings."
            ),
            UserProfile(
                user = userRepository.findById(26L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.WORKING),
                bio = "I have a love for fashion and enjoy keeping up with the latest trends and styles. Designing my own clothes and accessories is a creative outlet for me."
            )
        )

        // These 26 users are looking for a room in a flat
        val userProfiles2 = listOf(
            UserProfile(
                user = userRepository.findById(27L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.WORKING),
                bio = "I enjoy starting my day early and being productive. My work is my passion, and I love what I do."
                ),
            UserProfile(
                user = userRepository.findById(28L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.PARTY_GOER),
                bio = "I thrive in the nightlife and enjoy socializing with friends. Keeping my space tidy helps me stay organized."
            ),
            UserProfile(
                user = userRepository.findById(29L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.STUDENT),
                bio = "I love spending time at home, especially when studying. Learning new things is my favorite pastime."
            ),
            UserProfile(
                user = userRepository.findById(30L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "I enjoy a lively social life and balancing it with my work. My space might be messy, but I know where everything is."
            ),
            UserProfile(
                user = userRepository.findById(31L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.HOMEBODY),
                bio = "I love staying up late and enjoying quiet nights at home. My carefree attitude helps me stay relaxed."
            ),
            UserProfile(
                user = userRepository.findById(32L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "I start my day early to make the most of my study time. Keeping my environment spotless helps me focus."
            ),
            UserProfile(
                user = userRepository.findById(33L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.WORKING),
                bio = "I enjoy the comfort of my home and working from there. A tidy space keeps me productive and happy."
            ),
            UserProfile(
                user = userRepository.findById(34L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.STUDENT),
                bio = "Balancing my studies with a fun social life is important to me. I keep things casual and enjoy the best of both worlds."
            ),
            UserProfile(
                user = userRepository.findById(35L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.WORKING),
                bio = "I work best at night and don't mind a bit of mess. My workspace reflects my creative chaos."
            ),
            UserProfile(
                user = userRepository.findById(36L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.HOMEBODY),
                bio = "I love early mornings and the peace of home. My carefree nature keeps me stress-free and happy."
            ),
            UserProfile(
                user = userRepository.findById(37L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.STUDENT),
                bio = "Studying late into the night is my routine. A spotless environment helps me stay focused and efficient."
            ),
            UserProfile(
                user = userRepository.findById(38L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.HOMEBODY),
                bio = "I enjoy hosting parties at home and keeping my space tidy. Balancing social life and relaxation is key for me."
            ),
            UserProfile(
                user = userRepository.findById(39L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.WORKING),
                bio = "I start my day early and keep things casual. My work is important, but so is my relaxed approach to life."
            ),
            UserProfile(
                user = userRepository.findById(40L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.STUDENT),
                bio = "I study best at night and don't mind a bit of mess. My space reflects my creative and academic pursuits."
            ),
            UserProfile(
                user = userRepository.findById(41L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "I love a good party and balancing it with my work. My carefree attitude helps me enjoy life to the fullest."
            ),
            UserProfile(
                user = userRepository.findById(42L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.STUDENT),
                bio = "I enjoy studying at home in a spotless environment. My focus and dedication help me achieve my goals."
            ),
            UserProfile(
                user = userRepository.findById(43L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.PARTY_GOER),
                bio = "I start my day early and enjoy socializing. Keeping my space tidy helps me stay organized and ready for fun."
            ),
            UserProfile(
                user = userRepository.findById(44L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.HOMEBODY),
                bio = "I love quiet nights at home and keeping things casual. My lifestyle is all about comfort and relaxation."
            ),
            UserProfile(
                user = userRepository.findById(45L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "I start my day early and don't mind a bit of mess. My focus is on my studies and achieving my academic goals."
            ),
            UserProfile(
                user = userRepository.findById(46L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.WORKING),
                bio =
                    "I work best at night and have a carefree attitude. My lifestyle helps me stay relaxed and productive."
            ),
            UserProfile(
                user = userRepository.findById(47L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.WORKING),
                bio = "I love starting my day early and being productive. My work is my passion, and I strive for excellence."
                ),
            UserProfile(
                user = userRepository.findById(48L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.PARTY_GOER),
                bio = "I thrive in the nightlife and enjoy socializing with friends. Keeping my space tidy helps me stay organized."
            ),
            UserProfile(
                user = userRepository.findById(49L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.STUDENT),
                bio = "I love spending time at home, especially when studying. Learning new things is my favorite pastime."
            ),
            UserProfile(
                user = userRepository.findById(50L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "I enjoy a lively social life and balancing it with my work. My space might be messy, but I know where everything is."
            ),
            UserProfile(
                user = userRepository.findById(51L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.HOMEBODY),
                bio = "I love staying up late and enjoying quiet nights at home. My carefree attitude helps me stay relaxed."
            ),
            UserProfile(
                user = userRepository.findById(52L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "I start my day early to make the most of my study time. Keeping my environment spotless helps me focus."
                )
        )

        // These 26 users live in a flat and are looking for (a) roommate(s)
        val userProfiles3 = listOf(
            UserProfile(
                user = userRepository.findById(53L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.WORKING),
                bio = "I love starting my day early and being productive. My work is my passion, and I strive for excellence."
            ),
            UserProfile(
                user = userRepository.findById(54L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.PARTY_GOER),
                bio = "I thrive in the nightlife and enjoy socializing with friends. Keeping my space tidy helps me stay organized."
            ),
            UserProfile(
                user = userRepository.findById(55L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.STUDENT),
                bio = "I love spending time at home, especially when studying. Learning new things is my favorite pastime."
            ),
            UserProfile(
                user = userRepository.findById(56L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "I enjoy a lively social life and balancing it with my work. My space might be messy, but I know where everything is."
            ),
            UserProfile(
                user = userRepository.findById(57L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.HOMEBODY),
                bio = "I love staying up late and enjoying quiet nights at home. My carefree attitude helps me stay relaxed."
            ),
            UserProfile(
                user = userRepository.findById(58L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "I start my day early to make the most of my study time. Keeping my environment spotless helps me focus."
            ),
            UserProfile(
                user = userRepository.findById(59L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.WORKING),
                bio = "I enjoy the comfort of my home and working from there. A tidy space keeps me productive and happy."
            ),
            UserProfile(
                user = userRepository.findById(60L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.STUDENT),
                bio = "Balancing my studies with a fun social life is important to me. I keep things casual and enjoy the best of both worlds."
            ),
            UserProfile(
                user = userRepository.findById(61L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.WORKING),
                bio = "I work best at night and don't mind a bit of mess. My workspace reflects my creative chaos."
            ),
            UserProfile(
                user = userRepository.findById(62L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.HOMEBODY),
                bio = "I love early mornings and the peace of home. My carefree nature keeps me stress-free and happy."
            ),
            UserProfile(
                user = userRepository.findById(63L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.STUDENT),
                bio = "Studying late into the night is my routine. A spotless environment helps me stay focused and efficient."
            ),
            UserProfile(
                user = userRepository.findById(64L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.HOMEBODY),
                bio = "I enjoy hosting parties at home and keeping my space tidy. Balancing social life and relaxation is key for me."
            ),
            UserProfile(
                user = userRepository.findById(65L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.WORKING),
                bio = "I start my day early and keep things casual. My work is important, but so is my relaxed approach to life."
            ),
            UserProfile(
                user = userRepository.findById(66L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.STUDENT),
                bio = "I study best at night and don't mind a bit of mess. My space reflects my creative and academic pursuits."
            ),
            UserProfile(
                user = userRepository.findById(67L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "I love a good party and balancing it with my work. My carefree attitude helps me enjoy life to the fullest."
            ),
            UserProfile(
                user = userRepository.findById(68L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.STUDENT),
                bio = "I enjoy studying at home in a spotless environment. My focus and dedication help me achieve my goals."
            ),
            UserProfile(
                user = userRepository.findById(69L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.PARTY_GOER),
                bio = "I start my day early and enjoy socializing. Keeping my space tidy helps me stay organized and ready for fun."
            ),
            UserProfile(
                user = userRepository.findById(70L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.HOMEBODY),
                bio = "I love quiet nights at home and keeping things casual. My lifestyle is all about comfort and relaxation."
            ),
            UserProfile(
                user = userRepository.findById(71L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "I start my day early and don't mind a bit of mess. My focus is on my studies and achieving my academic goals."
            ),
            UserProfile(
                user = userRepository.findById(72L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.WORKING),
                bio = "I work best at night and have a carefree attitude. My lifestyle helps me stay relaxed and productive."
            ),
            UserProfile(
                user = userRepository.findById(73L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.WORKING),
                bio = "I love starting my day early and being productive. My work is my passion, and I strive for excellence."
            ),
            UserProfile(
                user = userRepository.findById(74L).get(),
                cleanliness = Cleanliness.TIDY,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.PARTY_GOER),
                bio = "I thrive in the nightlife and enjoy socializing with friends. Keeping my space tidy helps me stay organized."
            ),
            UserProfile(
                user = userRepository.findById(75L).get(),
                cleanliness = Cleanliness.CASUAL,
                lifestyle = mutableSetOf(Lifestyle.HOMEBODY, Lifestyle.STUDENT),
                bio = "I love spending time at home, especially when studying. Learning new things is my favorite pastime."
            ),
            UserProfile(
                user = userRepository.findById(76L).get(),
                cleanliness = Cleanliness.MESSY,
                lifestyle = mutableSetOf(Lifestyle.PARTY_GOER, Lifestyle.WORKING),
                bio = "I enjoy a lively social life and balancing it with my work. My space might be messy, but I know where everything is."
            ),
            UserProfile(
                user = userRepository.findById(77L).get(),
                cleanliness = Cleanliness.CAREFREE,
                lifestyle = mutableSetOf(Lifestyle.NIGHT_OWL, Lifestyle.HOMEBODY),
                bio = "I love staying up late and enjoying quiet nights at home. My carefree attitude helps me stay relaxed."
            ),
            UserProfile(
                user = userRepository.findById(78L).get(),
                cleanliness = Cleanliness.SPOTLESS,
                lifestyle = mutableSetOf(Lifestyle.EARLY_BIRD, Lifestyle.STUDENT),
                bio = "I start my day early to make the most of my study time. Keeping my environment spotless helps me focus."
                )
        )

        // Save all user profiles to database
        profileRepository.saveAll(userProfiles1 + userProfiles2 + userProfiles3)
    }
}