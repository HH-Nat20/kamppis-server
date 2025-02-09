package nat20.kamppisserver.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Test class for ValidationService. Tests that isValidString, isValidEmail,
 * and isValidPassword return true or false accordingly.
 */
class ValidationServiceTest() {

    private val service = ValidationService()

    @Test
    fun `isValidString should return true for valid strings`() {
        assertTrue(service.isValidString("Jean-Luc O'Connor"))
    }

    @Test
    fun `isValidString should return false for invalid strings`() {
        assertFalse(service.isValidString("John123"))
        assertFalse(service.isValidString("John@Doe"))
        assertFalse(service.isValidString(""))
    }

    @Test
    fun `isValidEmail should return true for valid emails`() {
        assertTrue(service.isValidEmail("user@example.com"))
        assertTrue(service.isValidEmail("test.email+alias@domain.co.uk"))
    }

    @Test
    fun `isValidEmail should return false for invalid emails`() {
        assertFalse(service.isValidEmail("userexample.com"))
        assertFalse(service.isValidEmail("user@.com"))
        assertFalse(service.isValidEmail("user@domain"))
        assertFalse(service.isValidEmail("user@domain."))
    }

    @Test
    fun `isValidPassword should return true for valid passwords`() {
        assertTrue(service.isValidPassword("Valid123"))
        assertTrue(service.isValidPassword("P@ssw0rd123"))
    }

    @Test
    fun `isValidPassword should return false for invalid passwords`() {
        assertFalse(service.isValidPassword("Short1"))
        assertFalse(service.isValidPassword("nouppercase1"))
        assertFalse(service.isValidPassword("NOLOWERCASE1"))
        assertFalse(service.isValidPassword("NoooDigits"))
    }
}