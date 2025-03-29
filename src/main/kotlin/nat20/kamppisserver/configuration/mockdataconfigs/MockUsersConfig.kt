package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.UserRepository
import java.time.LocalDate

class MockUsersConfig {

    /**
     * Database initializer for adding mock user data into the database
     */
    fun insertMockUsersToDatabase(
        userRepository: UserRepository
    ) {
        // These 26 users are looking for another roommate to look for a flat together
        val users1 = listOf(
            User(
                email = "alice.smith@example.com",
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 5, 14),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "bob.johnson@example.com",
                firstName = "Bob",
                lastName = "Johnson",
                dateOfBirth = LocalDate.of(1985, 11, 22),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "charlie.davis@example.com",
                firstName = "Charlie",
                lastName = "Davis",
                dateOfBirth = LocalDate.of(1998, 2, 3),
                gender = Gender.OTHER,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "diana.lee@example.com",
                firstName = "Diana",
                lastName = "Lee",
                dateOfBirth = LocalDate.of(2000, 2, 3),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "eve.brown@example.com",
                firstName = "Eve",
                lastName = "Brown",
                dateOfBirth = LocalDate.of(1995, 8, 19),
                gender = Gender.OTHER,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "frank.miller@example.com",
                firstName = "Frank",
                lastName = "Miller",
                dateOfBirth = LocalDate.of(1988, 3, 22),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "grace.wilson@example.com",
                firstName = "Grace",
                lastName = "Wilson",
                dateOfBirth = LocalDate.of(1992, 7, 15),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "harry.moore@example.com",
                firstName = "Harry",
                lastName = "Moore",
                dateOfBirth = LocalDate.of(1985, 1, 10),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "isabella.taylor@example.com",
                firstName = "Isabella",
                lastName = "Taylor",
                dateOfBirth = LocalDate.of(1999, 12, 5),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "jack.anderson@example.com",
                firstName = "Jack",
                lastName = "Anderson",
                dateOfBirth = LocalDate.of(1990, 6, 30),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "karen.thomas@example.com",
                firstName = "Karen",
                lastName = "Thomas",
                dateOfBirth = LocalDate.of(1982, 8, 20),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "luke.jackson@example.com",
                firstName = "Luke",
                lastName = "Jackson",
                dateOfBirth = LocalDate.of(1995, 4, 2),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "mia.white@example.com",
                firstName = "Mia",
                lastName = "White",
                dateOfBirth = LocalDate.of(2000, 2, 17),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "nathan.harris@example.com",
                firstName = "Nathan",
                lastName = "Harris",
                dateOfBirth = LocalDate.of(1987, 9, 9),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "olivia.martin@example.com",
                firstName = "Olivia",
                lastName = "Martin",
                dateOfBirth = LocalDate.of(1993, 11, 11),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "peter.thompson@example.com",
                firstName = "Peter",
                lastName = "Thompson",
                dateOfBirth = LocalDate.of(1980, 5, 5),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "quinn.garcia@example.com",
                firstName = "Quinn",
                lastName = "Garcia",
                dateOfBirth = LocalDate.of(1998, 3, 3),
                gender = Gender.OTHER,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "rachel.martinez@example.com",
                firstName = "Rachel",
                lastName = "Martinez",
                dateOfBirth = LocalDate.of(1989, 10, 25),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "samuel.robinson@example.com",
                firstName = "Samuel",
                lastName = "Robinson",
                dateOfBirth = LocalDate.of(1978, 12, 1),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "tina.clark@example.com",
                firstName = "Tina",
                lastName = "Clark",
                dateOfBirth = LocalDate.of(1996, 7, 19),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "ursula.rodriguez@example.com",
                firstName = "Ursula",
                lastName = "Rodriguez",
                dateOfBirth = LocalDate.of(1991, 1, 29),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "victor.lewis@example.com",
                firstName = "Victor",
                lastName = "Lewis",
                dateOfBirth = LocalDate.of(1984, 4, 14),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "wendy.walker@example.com",
                firstName = "Wendy",
                lastName = "Walker",
                dateOfBirth = LocalDate.of(1994, 6, 8),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "xavier.hall@example.com",
                firstName = "Xavier",
                lastName = "Hall",
                dateOfBirth = LocalDate.of(1986, 9, 30),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "yvonne.allen@example.com",
                firstName = "Yvonne",
                lastName = "Allen",
                dateOfBirth = LocalDate.of(1997, 2, 22),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "zachary.young@example.com",
                firstName = "Zachary",
                lastName = "Young",
                dateOfBirth = LocalDate.of(1983, 11, 12),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            )
        )

        // These 26 users are looking for a room in a flat
        val users2 = listOf(
            User(
                email = "allen.oaks@example.com",
                firstName = "Allen",
                lastName = "Oaks",
                dateOfBirth = LocalDate.of(1990, 5, 14),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "brian.johnson@example.com",
                firstName = "Brian",
                lastName = "Johnson",
                dateOfBirth = LocalDate.of(1987, 8, 22),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "carol.davis@example.com",
                firstName = "Carol",
                lastName = "Davis",
                dateOfBirth = LocalDate.of(2002, 3, 9),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "david.miller@example.com",
                firstName = "David",
                lastName = "Miller",
                dateOfBirth = LocalDate.of(1995, 12, 30),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "emma.wilson@example.com",
                firstName = "Emma",
                lastName = "Wilson",
                dateOfBirth = LocalDate.of(2000, 7, 18),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "fredrick.atwood@example.com",
                firstName = "Fredrick",
                lastName = "Atwood",
                dateOfBirth = LocalDate.of(1992, 11, 3),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "gabby.taylor@example.com",
                firstName = "Gabby",
                lastName = "Taylor",
                dateOfBirth = LocalDate.of(1998, 11, 3),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "henry.anderson@example.com",
                firstName = "Henry",
                lastName = "Anderson",
                dateOfBirth = LocalDate.of(1993, 6, 17),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "imogen.thomas@example.com",
                firstName = "Imogen",
                lastName = "Thomas",
                dateOfBirth = LocalDate.of(2001, 9, 29),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "jackson.jackson@example.com",
                firstName = "Jackson",
                lastName = "Jackson",
                dateOfBirth = LocalDate.of(1996, 2, 14),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "kelly.white@example.com",
                firstName = "Kelly",
                lastName = "White",
                dateOfBirth = LocalDate.of(1989, 12, 5),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "liam.harris@example.com",
                firstName = "Liam",
                lastName = "Harris",
                dateOfBirth = LocalDate.of(1997, 3, 21),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "mary.martin@example.com",
                firstName = "Mary",
                lastName = "Martin",
                dateOfBirth = LocalDate.of(2004, 8, 10),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "noah.lee@example.com",
                firstName = "Noah",
                lastName = "Lee",
                dateOfBirth = LocalDate.of(1992, 1, 7),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "olga.walker@example.com",
                firstName = "Olga",
                lastName = "Walker",
                dateOfBirth = LocalDate.of(1994, 10, 23),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "paul.hall@example.com",
                firstName = "Paul",
                lastName = "Hall",
                dateOfBirth = LocalDate.of(1986, 5, 30),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "quentin.allen@example.com",
                firstName = "Quentin",
                lastName = "Allen",
                dateOfBirth = LocalDate.of(2003, 7, 19),
                gender = Gender.OTHER,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "rita.young@example.com",
                firstName = "Rita",
                lastName = "Young",
                dateOfBirth = LocalDate.of(1991, 11, 11),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "solomon.king@example.com",
                firstName = "Solomon",
                lastName = "King",
                dateOfBirth = LocalDate.of(1999, 6, 6),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "thomas.scott@example.com",
                firstName = "Thomas",
                lastName = "Scott",
                dateOfBirth = LocalDate.of(1990, 4, 15),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "uma.brown@example.com",
                firstName = "Uma",
                lastName = "Brown",
                dateOfBirth = LocalDate.of(1998, 12, 2),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "vasili.adams@example.com",
                firstName = "Vasili",
                lastName = "Adams",
                dateOfBirth = LocalDate.of(1995, 3, 15),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "wesley.brown@example.com",
                firstName = "Wesley",
                lastName = "Brown",
                dateOfBirth = LocalDate.of(1998, 7, 22),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "xerxes.clark@example.com",
                firstName = "Xerxes",
                lastName = "Clark",
                dateOfBirth = LocalDate.of(1992, 11, 30),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "yasmine.davis@example.com",
                firstName = "Yasmine",
                lastName = "Davis",
                dateOfBirth = LocalDate.of(2000, 5, 10),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "zane.evans@example.com",
                firstName = "Zane",
                lastName = "Evans",
                dateOfBirth = LocalDate.of(1997, 8, 5),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            )
        )

        // These 26 users live in a flat and are looking for (a) roommate(s)
        val users3 = listOf(
            User(
                email = "aaron.baker@example.com",
                firstName = "Aaron",
                lastName = "Baker",
                dateOfBirth = LocalDate.of(1991, 2, 5),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "bella.evans@example.com",
                firstName = "Bella",
                lastName = "Evans",
                dateOfBirth = LocalDate.of(1993, 7, 12),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "claude.green@example.com",
                firstName = "Claude",
                lastName = "Green",
                dateOfBirth = LocalDate.of(1988, 11, 20),
                gender = Gender.OTHER,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "dustin.hill@example.com",
                firstName = "Dustin",
                lastName = "Hill",
                dateOfBirth = LocalDate.of(1999, 4, 8),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "ethan.james@example.com",
                firstName = "Ethan",
                lastName = "James",
                dateOfBirth = LocalDate.of(2000, 9, 25),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "fiona.kelly@example.com",
                firstName = "Fiona",
                lastName = "Kelly",
                dateOfBirth = LocalDate.of(1995, 6, 3),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "george.lewis@example.com",
                firstName = "George",
                lastName = "Lewis",
                dateOfBirth = LocalDate.of(1986, 12, 14),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "hannah.morris@example.com",
                firstName = "Hannah",
                lastName = "Morris",
                dateOfBirth = LocalDate.of(1992, 3, 19),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "ian.morgan@example.com",
                firstName = "Ian",
                lastName = "Morgan",
                dateOfBirth = LocalDate.of(1994, 8, 30),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "julia.parker@example.com",
                firstName = "Julia",
                lastName = "Parker",
                dateOfBirth = LocalDate.of(2003, 5, 22),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "kevin.reed@example.com",
                firstName = "Kevin",
                lastName = "Reed",
                dateOfBirth = LocalDate.of(1997, 1, 10),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "laura.ross@example.com",
                firstName = "Laura",
                lastName = "Ross",
                dateOfBirth = LocalDate.of(2002, 11, 27),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "michael.scott@example.com",
                firstName = "Michael",
                lastName = "Scott",
                dateOfBirth = LocalDate.of(1989, 6, 15),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "nina.turner@example.com",
                firstName = "Nina",
                lastName = "Turner",
                dateOfBirth = LocalDate.of(1996, 10, 5),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "oliver.lee@example.com",
                firstName = "Oliver",
                lastName = "Lee",
                dateOfBirth = LocalDate.of(1996, 9, 30),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "patricia.martin@example.com",
                firstName = "Patricia",
                lastName = "Martin",
                dateOfBirth = LocalDate.of(1991, 11, 25),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "quency.nelson@example.com",
                firstName = "Quency",
                lastName = "Nelson",
                dateOfBirth = LocalDate.of(1993, 4, 18),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "rose.owens@example.com",
                firstName = "Rose",
                lastName = "Owens",
                dateOfBirth = LocalDate.of(2000, 8, 7),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "steven.parker@example.com",
                firstName = "Steven",
                lastName = "Parker",
                dateOfBirth = LocalDate.of(1997, 12, 3),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "tamara.quinn@example.com",
                firstName = "Tamara",
                lastName = "Quinn",
                dateOfBirth = LocalDate.of(1995, 5, 19),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "uwe.roberts@example.com",
                firstName = "Uwe",
                lastName = "Roberts",
                dateOfBirth = LocalDate.of(1998, 1, 12),
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "veronica.smith@example.com",
                firstName = "Veronica",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1992, 3, 27),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "winona.thompson@example.com",
                firstName = "Winona",
                lastName = "Thompson",
                dateOfBirth = LocalDate.of(1994, 10, 15),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "ximena.underwood@example.com",
                firstName = "Ximena",
                lastName = "Underwood",
                dateOfBirth = LocalDate.of(1996, 6, 9),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "yara.vasquez@example.com",
                firstName = "Yara",
                lastName = "Vasquez",
                dateOfBirth = LocalDate.of(2001, 7, 23),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            ),
            User(
                email = "zoey.williams@example.com",
                firstName = "Zoey",
                lastName = "Williams",
                dateOfBirth = LocalDate.of(1990, 11, 5),
                gender = Gender.FEMALE,
                status = UserStatus.ACTIVE
            )
        )

        // Save all mock users to the database
        userRepository.saveAll(users1 + users2 + users3)
    }
}