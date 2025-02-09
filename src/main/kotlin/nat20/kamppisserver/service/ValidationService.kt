package nat20.kamppisserver.service

/**
 * Service class for input validation and sanitization. Can be used to sanitize
 * user inputs in forms and search bars, GET and POST requests, cookies, and more.
 */
class ValidationService {

    /**
     * Validates String values by checking for whitelisted characters.
     *
     * @param string to be validated.
     * @return true if includes only whitelisted characters (letters, apostrophes ('),
     * and hyphens (-). False otherwise.
     */
    fun isValidString(string: String): Boolean =
        string.matches(Regex("^[a-zA-Z'-]+$"))

    /**
     * Validates e-mails.
     * @param email to be validated.
     * @return true if matches default e-mail pattern. False otherwise.
     */
    fun isValidEmail(email: String): Boolean =
        email.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))

    /**
     * Validates passwords. Might not be needed due to OAuth.
     * @param password to be validated.
     * @return true if includes required characters. False otherwise.
     */
    fun isValidPassword(password: String): Boolean {
        return when {
            password.length < 8 -> false
            password.none { it.isUpperCase() } -> false
            password.none { it.isLowerCase() } -> false
            password.none { it.isDigit() } -> false
            else -> true
        }
    }
}