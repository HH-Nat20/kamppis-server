package nat20.kamppisserver

import java.security.Principal

/**
 * This class mocks authenticated users through a simulated Principal object as
 * found in Java's Principal interface. The mock user has only one attribute,
 * name.
 */
class TestPrincipal(private val name: String) : Principal {
    override fun getName(): String = name
}