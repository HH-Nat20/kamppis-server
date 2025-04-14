package nat20.kamppisserver.service

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.RoommatePreference
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * Service class for input validation.
 */
class ValidationService {

    /**
     * Custom annotation to check that min age preference is not greater than max age preference
     * Used in RoommatePreferences entity
     */
    @Constraint(validatedBy = [AgePreferenceValidator::class])
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ValidAgePreferences(
        val message: String = "Min age preference must be less than max age preference",
        val groups: Array<KClass<*>> = [], //Mandatory, can be left empty for simple validations
        val payload: Array<KClass<out Payload>> = [] //Mandatory, can be left empty for simple validations
    )

    /**
     * Custom validator to check that min age preference is not greater than max age preference
     * Used in RoommatePreferences entity
     */
    @Component
    class AgePreferenceValidator: ConstraintValidator<ValidAgePreferences, RoommatePreference> {
        override fun isValid(preferences: RoommatePreference, context: ConstraintValidatorContext): Boolean {
            if (preferences.minAgePreference == null || preferences.maxAgePreference == null) {
                return true // Validation passes because comparison is not possible with a null value
            }

            if (preferences.minAgePreference!! >= preferences.maxAgePreference!!) {
                // Disables default validation message
                context.disableDefaultConstraintViolation()

                // Creates new custom error message (does not automatically use annotation's message)
                context.buildConstraintViolationWithTemplate("Min age preference must be less than max age preference")
                    // Binds the error message to the correct field
                    .addPropertyNode("minAgePreference")
                    // Finalizes error configuration
                    .addConstraintViolation()

                return false // Validation fails
            }

            return true // Validation passes
        }
    }

    /**
     * Custom annotation to check that furnishedInfo is present if furnished is true
     * Used in RoomProfile entity
     */
    @Constraint(validatedBy = [FurnishedInfoValidator::class])
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ValidFurnishedInfo(
        val message: String = "FurnishedInfo must be a string if furnished is true OR null if furnished is false",
        val groups: Array<KClass<*>> = [], //Mandatory, can be left empty for simple validations
        val payload: Array<KClass<out Payload>> = [] //Mandatory, can be left empty for simple validations
    )

    /**
     * Custom validator to check that furnishedInfo is present if furnished is true
     * Used in RoomProfile entity
     */
    @Component
    class FurnishedInfoValidator: ConstraintValidator<ValidFurnishedInfo, RoomProfile> {
        override fun isValid(roomProfile: RoomProfile, context: ConstraintValidatorContext): Boolean {
            if (roomProfile.furnished && roomProfile.furnishedInfo.isNullOrEmpty()) {
                // Disables default validation message
                context.disableDefaultConstraintViolation()

                // Creates new custom error message (does not automatically use annotation's message)
                // We use roomProfile.flat.id because roomProfile is not saved to database -> roomProfile has no id
                context.buildConstraintViolationWithTemplate("❌RoomProfile validation for a room in flat id ${roomProfile.flat.id}: If furnished is true, furnishedInfo must not be null or empty")
                    // Binds the error message to the correct field
                    .addPropertyNode("furnishedInfo")
                    // Finalizes error configuration
                    .addConstraintViolation()

                return false // Validation fails
            }

            if (!roomProfile.furnished && roomProfile.furnishedInfo != null) {
                // Disables default validation message
                context.disableDefaultConstraintViolation()

                // Creates new custom error message (does not automatically use annotation's message)
                // We use roomProfile.flat.id because roomProfile is not saved to database -> roomProfile has no id
                context.buildConstraintViolationWithTemplate("❌RoomProfile validation for a room in flat id ${roomProfile.flat.id}: If furnished is false, furnishedInfo must be null")
                    // Binds the error message to the correct field
                    .addPropertyNode("furnishedInfo")
                    // Finalizes error configuration
                    .addConstraintViolation()

                return false // Validation fails
            }

            return true // Validation passes
        }
    }
}