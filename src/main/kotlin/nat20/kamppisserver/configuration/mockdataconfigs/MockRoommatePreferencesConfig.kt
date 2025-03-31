package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.RoommatePreference
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.repository.RoommatePreferenceRepository
import nat20.kamppisserver.repository.UserRepository

class MockRoommatePreferencesConfig {

    /**
     * Database initializer for adding mock roommate preferences data into the database
     */
    fun insertMockRoommatePreferencesToDatabase(
        userRepository: UserRepository,
        roommatePreferenceRepository: RoommatePreferenceRepository
    ) {
        // These 26 users are looking for another roommate to look for a flat together
        val roommatePreferences1 = listOf(
            RoommatePreference(
                user = userRepository.findById(1L).get(),
                minAgePreference = 20,
                maxAgePreference = 29,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO),
            ),
            RoommatePreference(
                user = userRepository.findById(2L).get(),
                minAgePreference = 22,
                maxAgePreference = 39,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO),
            ),
            RoommatePreference(
                user = userRepository.findById(3L).get(),
                minAgePreference = 18,
                maxAgePreference = 28,
                genderPreferences = mutableListOf(Gender.OTHER, Gender.FEMALE),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO),
            ),
            RoommatePreference(
                user = userRepository.findById(4L).get(),
                minAgePreference = 29,
                maxAgePreference = 40,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(5L).get(),
                minAgePreference = 38,
                maxAgePreference = 57,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(6L).get(),
                minAgePreference = 20,
                maxAgePreference = 33,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(7L).get(),
                minAgePreference = 23,
                maxAgePreference = 32,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(8L).get(),
                minAgePreference = 18,
                maxAgePreference = 28,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(9L).get(),
                minAgePreference = 21,
                maxAgePreference = 30,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(10L).get(),
                minAgePreference = 22,
                maxAgePreference = 27,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(11L).get(),
                minAgePreference = 24,
                maxAgePreference = 34,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(12L).get(),
                minAgePreference = 19,
                maxAgePreference = 31,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO),
            ),
            RoommatePreference(
                user = userRepository.findById(13L).get(),
                minAgePreference = 18,
                maxAgePreference = 33,
                genderPreferences = mutableListOf(Gender.MALE, Gender.FEMALE),
                locationPreferences = null,
            ),
            RoommatePreference(
                user = userRepository.findById(14L).get(),
                minAgePreference = 20,
                maxAgePreference = 35,
                genderPreferences = mutableListOf(Gender.MALE, Gender.FEMALE),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO),
            ),
            RoommatePreference(
                user = userRepository.findById(15L).get(),
                minAgePreference = 21,
                maxAgePreference = 33,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(16L).get(),
                minAgePreference = 23,
                maxAgePreference = 36,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(17L).get(),
                minAgePreference = 18,
                maxAgePreference = 29,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(18L).get(),
                minAgePreference = 19,
                maxAgePreference = 31,
                genderPreferences = mutableListOf(Gender.MALE, Gender.FEMALE),
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(19L).get(),
                minAgePreference = 24,
                maxAgePreference = 38,
                genderPreferences = mutableListOf(Gender.MALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO),
            ),
            RoommatePreference(
                user = userRepository.findById(20L).get(),
                minAgePreference = 20,
                maxAgePreference = 33,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(21L).get(),
                minAgePreference = 21,
                maxAgePreference = 35,
                genderPreferences = null,
                locationPreferences = null,
            ),
            RoommatePreference(
                user = userRepository.findById(22L).get(),
                minAgePreference = 20,
                maxAgePreference = 36,
                genderPreferences = mutableListOf(Gender.MALE, Gender.FEMALE),
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(23L).get(),
                minAgePreference = 21,
                maxAgePreference = 33,
                genderPreferences = null,
                locationPreferences = null,
            ),
            RoommatePreference(
                user = userRepository.findById(24L).get(),
                minAgePreference = 19,
                maxAgePreference = 36,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(25L).get(),
                minAgePreference = 20,
                maxAgePreference = 33,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER),
                locationPreferences = mutableListOf(City.VANTAA, City.ESPOO),
            ),
            RoommatePreference(
                user = userRepository.findById(26L).get(),
                minAgePreference = 21,
                maxAgePreference = 32,
                genderPreferences = null,
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
            )
        )

        // These 26 users are looking for roommate(s) to their own flat
        val roommatePreferences2 = listOf(
            RoommatePreference(
                user = userRepository.findById(53L).get(),
                minAgePreference = 20,
                maxAgePreference = 29,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER)
            ),
            RoommatePreference(
                user = userRepository.findById(54L).get(),
                minAgePreference = 20,
                maxAgePreference = 29,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.OTHER)
            ),
            RoommatePreference(
                user = userRepository.findById(55L).get(),
                minAgePreference = 18,
                maxAgePreference = 25,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(56L).get(),
                minAgePreference = 21,
                maxAgePreference = 28,
                genderPreferences = mutableListOf(Gender.FEMALE)
            ),
            RoommatePreference(
                user = userRepository.findById(57L).get(),
                minAgePreference = 20,
                maxAgePreference = 28,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(58L).get(),
                minAgePreference = 20,
                maxAgePreference = 28,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(59L).get(),
                minAgePreference = 20,
                maxAgePreference = 28,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(60L).get(),
                minAgePreference = 22,
                maxAgePreference = 29,
                genderPreferences = mutableListOf(Gender.MALE, Gender.FEMALE)
            ),
            RoommatePreference(
                user = userRepository.findById(61L).get(),
                minAgePreference = 22,
                maxAgePreference = 29,
                genderPreferences = mutableListOf(Gender.MALE, Gender.FEMALE)
            ),
            RoommatePreference(
                user = userRepository.findById(62L).get(),
                minAgePreference = 19,
                maxAgePreference = 28,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(63L).get(),
                minAgePreference = 19,
                maxAgePreference = 28,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(64L).get(),
                minAgePreference = 22,
                maxAgePreference = 29,
                genderPreferences = mutableListOf(Gender.MALE, Gender.OTHER)
            ),
            RoommatePreference(
                user = userRepository.findById(65L).get(),
                minAgePreference = 18,
                maxAgePreference = 32,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(66L).get(),
                minAgePreference = 18,
                maxAgePreference = 32,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(67L).get(),
                minAgePreference = 21,
                maxAgePreference = 35,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(68L).get(),
                minAgePreference = 21,
                maxAgePreference = 35,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(69L).get(),
                minAgePreference = 21,
                maxAgePreference = 35,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(70L).get(),
                minAgePreference = 23,
                maxAgePreference = 30,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.MALE)
            ),
            RoommatePreference(
                user = userRepository.findById(71L).get(),
                minAgePreference = 23,
                maxAgePreference = 30,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.MALE)
            ),
            RoommatePreference(
                user = userRepository.findById(72L).get(),
                minAgePreference = 20,
                maxAgePreference = 28,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.MALE)
            ),
            RoommatePreference(
                user = userRepository.findById(73L).get(),
                minAgePreference = 19,
                maxAgePreference = 27,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(74L).get(),
                minAgePreference = 24,
                maxAgePreference = 32,
                genderPreferences = mutableListOf(Gender.OTHER)
            ),
            RoommatePreference(
                user = userRepository.findById(75L).get(),
                minAgePreference = 21,
                maxAgePreference = 34,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.MALE)
            ),
            RoommatePreference(
                user = userRepository.findById(76L).get(),
                minAgePreference = 21,
                maxAgePreference = 34,
                genderPreferences = mutableListOf(Gender.FEMALE, Gender.MALE)
            ),
            RoommatePreference(
                user = userRepository.findById(77L).get(),
                minAgePreference = 23,
                maxAgePreference = 37,
                genderPreferences = null
            ),
            RoommatePreference(
                user = userRepository.findById(78L).get(),
                minAgePreference = 23,
                maxAgePreference = 37,
                genderPreferences = null
            )
        )

        // Save all mock roommate preferences to database
        roommatePreferenceRepository.saveAll(roommatePreferences1 + roommatePreferences2)
    }
}