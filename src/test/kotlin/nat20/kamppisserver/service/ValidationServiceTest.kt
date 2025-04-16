package nat20.kamppisserver.service

import org.junit.jupiter.api.Assertions.*
import jakarta.validation.Validation
import jakarta.validation.Validator
import nat20.kamppisserver.domain.Flat
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.RoommatePreference
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.domain.enums.LookingFor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ValidationServiceTest {

    private lateinit var validator: Validator

    private val user: User = User(
        email = "john.doe@example.com",
        firstName = "John",
        lastName = "Doe",
        dateOfBirth = LocalDate.of(1990, 5, 14),
        gender = Gender.MALE,
        lookingFor = LookingFor.OTHER_USER_PROFILES
    )

    private val flat: Flat = Flat(
        name = "Test",
        description = "Test flat",
        location = City.HELSINKI,
        totalRoommates = 2,
        petHousehold = false
    )

    private val roomProfile: RoomProfile = RoomProfile(
        flat = flat,
        furnished = false,
        users = mutableListOf(user),
        rent = 1000,
        isPrivateRoom = false,
        bio = "Test RoomProfile"
    )

    @BeforeEach
    fun setup() {
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    @Test
    fun `should fail when minAge is greater than maxAge`() {
        val invalidPreference = RoommatePreference(
            minAgePreference = 30,
            maxAgePreference = 20,
            user = user,
            genderPreferences = mutableListOf(Gender.MALE),
            locationPreferences = mutableListOf(City.HELSINKI)
        )

        val violations = validator.validate(invalidPreference)

        assertEquals(1, violations.size)
        assertEquals("Min age preference must be less than max age preference", violations.first().message)
        assertEquals("minAgePreference", violations.first().propertyPath.toString())
    }

    @Test
    fun `should pass when minAge is less than maxAge`() {
        val validPreference = RoommatePreference(
            minAgePreference = 20,
            maxAgePreference = 30,
            user = user,
            genderPreferences = mutableListOf(Gender.MALE),
            locationPreferences = mutableListOf(City.HELSINKI)
        )

        val violations = validator.validate(validPreference)

        assertEquals(0, violations.size)
    }

    @Test
    fun `should pass when one or both values are null`() {
        val withNullMin = RoommatePreference(
            minAgePreference = null,
            maxAgePreference = 30,
            user = user,
            genderPreferences = mutableListOf(Gender.MALE),
            locationPreferences = mutableListOf(City.HELSINKI)
        )

        val withNullMax = RoommatePreference(
            minAgePreference = 20,
            maxAgePreference = null,
            user = user,
            genderPreferences = mutableListOf(Gender.MALE),
            locationPreferences = mutableListOf(City.HELSINKI)
        )

        val withBothNull = RoommatePreference(
            minAgePreference = null,
            maxAgePreference = null,
            user = user,
            genderPreferences = mutableListOf(Gender.MALE),
            locationPreferences = mutableListOf(City.HELSINKI)
        )

        assertEquals(0, validator.validate(withNullMin).size)
        assertEquals(0, validator.validate(withNullMax).size)
        assertEquals(0, validator.validate(withBothNull).size)
    }

    @Test
    fun `valid when furnished true and furnishedInfo is present`() {
        roomProfile.furnished = true
        roomProfile.furnishedInfo = "Test furniture"
        val violations = validator.validate(roomProfile)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `invalid when furnished true and furnishedInfo is null`() {
        roomProfile.furnished = true
        roomProfile.furnishedInfo = null
        val violations = validator.validate(roomProfile)
        assertEquals(1, violations.size)
        assertEquals("❌RoomProfile validation for a room in flat id ${roomProfile.flat.id}: If furnished is true, furnishedInfo must not be null or empty", violations.first().message)
        assertEquals("furnishedInfo", violations.first().propertyPath.toString())
    }

    @Test
    fun `invalid when furnished false and furnishedInfo is not null`() {
        roomProfile.furnished = false
        roomProfile.furnishedInfo = "Unnecessary info"
        val violations = validator.validate(roomProfile)
        assertEquals(1, violations.size)
        assertEquals("❌RoomProfile validation for a room in flat id ${roomProfile.flat.id}: If furnished is false, furnishedInfo must be null", violations.first().message)
        assertEquals("furnishedInfo", violations.first().propertyPath.toString())
    }

    @Test
    fun `valid when furnished false and furnishedInfo is null`() {
        val violations = validator.validate(roomProfile)
        assertTrue(violations.isEmpty())
    }
}