package nat20.kamppisserver.service

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import nat20.kamppisserver.domain.RoommatePreference
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * Service class for input validation and sanitization. Can be used to sanitize
 * user inputs in forms and search bars, GET and POST requests, cookies, and more.
 */
class ValidationService {

    /**
     * Validates String values by checking for whitelisted characters.
     */
    fun isValidString(string: String): Boolean {
        val sanitized = string.trim()

        // Only allow letters, spaces, apostrophes, and hyphens
        val validPattern = Regex("^[a-zA-Z' -]+$")

        // Check for control characters, null bytes, or other risky characters
        val riskyPattern = Regex("[\\x00-\\x1F<>\"&%;(){}=]")

        return sanitized.matches(validPattern) && !riskyPattern.containsMatchIn(sanitized)
    }

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
            if (preferences.minAgePreference >= preferences.maxAgePreference) {
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
}