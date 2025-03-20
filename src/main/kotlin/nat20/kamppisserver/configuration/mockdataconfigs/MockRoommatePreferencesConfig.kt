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
        val roommatePreferences = listOf(
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
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(6L).get(),
                minAgePreference = 20,
                maxAgePreference = 33,
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
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
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
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
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(11L).get(),
                minAgePreference = 24,
                maxAgePreference = 34,
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
                locationPreferences = mutableListOf(City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(12L).get(),
                minAgePreference = 19,
                maxAgePreference = 31,
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO),
            ),
            RoommatePreference(
                user = userRepository.findById(13L).get(),
                minAgePreference = 18,
                maxAgePreference = 33,
                genderPreferences = mutableListOf(Gender.MALE, Gender.FEMALE),
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA),
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
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(16L).get(),
                minAgePreference = 23,
                maxAgePreference = 36,
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
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
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
                locationPreferences = mutableListOf(City.HELSINKI, City.VANTAA),
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
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
            ),
            RoommatePreference(
                user = userRepository.findById(24L).get(),
                minAgePreference = 19,
                maxAgePreference = 36,
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
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
                genderPreferences = mutableListOf(Gender.NOT_IMPORTANT),
                locationPreferences = mutableListOf(City.HELSINKI, City.ESPOO, City.VANTAA),
            )
        )

        // Save all mock roommate preferences to database
        roommatePreferenceRepository.saveAll(roommatePreferences)
    }
}