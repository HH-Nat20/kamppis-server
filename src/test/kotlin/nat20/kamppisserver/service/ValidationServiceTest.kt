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

}