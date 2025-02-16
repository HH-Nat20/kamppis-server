package nat20.kamppisserver

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class StartupRunner{
    private val logger = LoggerFactory.getLogger(StartupRunner::class.java)

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady() {
        logger.info("\n \uD83D\uDE80 Spring Boot application has started successfully and the service is running! \uD83D\uDE80 \n" )    }
}