package nat20.kamppisserver

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class StartupRunner : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(StartupRunner::class.java)

    override fun run(vararg args: String?) {
        logger.info("\uD83D\uDE80 Spring Boot application has started successfully and the service is running! \uD83D\uDE80")    }
}