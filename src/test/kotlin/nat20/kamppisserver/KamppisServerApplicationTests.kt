package nat20.kamppisserver

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@Disabled("Skipping application context test since the database is not available")
@SpringBootTest()
class KamppisServerApplicationTests {

    @Test
    fun contextLoads() {
    }

}
