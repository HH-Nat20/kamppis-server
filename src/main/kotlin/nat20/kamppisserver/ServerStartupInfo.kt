package nat20.kamppisserver

import org.springframework.stereotype.Component
import java.time.Instant

/**
 * Provides information about the server's startup.
 * This component initializes the timestamp of the application's launch.
 */
@Component
class ServerStartupInfo {
    /**
     * The timestamp indicating when the server was marked as started.
     *
     * This value is initialized to the current time at the point of object creation
     * and represents the moment the server's startup process was completed.
     */
    val upSince: Instant = Instant.now()
}