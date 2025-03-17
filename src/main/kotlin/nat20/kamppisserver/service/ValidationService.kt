package nat20.kamppisserver.service

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

}