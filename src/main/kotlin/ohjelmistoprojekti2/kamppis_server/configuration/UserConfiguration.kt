package ohjelmistoprojekti2.kamppis_server.configuration

import ohjelmistoprojekti2.kamppis_server.domain.User
import ohjelmistoprojekti2.kamppis_server.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.LocalDate

@Configuration
@Profile("dev") // This config will only load when the dev profile is active from application.properties
class UserConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository) = ApplicationRunner {
        val users = listOf(
            User(
                email = "alice.smith@example.com",
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 5, 14)
            ),
            User(
                email = "bob.johnson@example.com",
                firstName = "Bob",
                lastName = "Johnson",
                dateOfBirth = LocalDate.of(1985, 11, 22)
            ),
            User(
                email = "charlie.davis@example.com",
                firstName = "Charlie",
                lastName = "Davis",
                dateOfBirth = null // Date of birth not provided
            ),
            User(
                email = "diana.lee@example.com",
                firstName = "Diana",
                lastName = "Lee",
                dateOfBirth = LocalDate.of(2000, 2, 3)
            ),
            User(
                email = "eve.brown@example.com",
                firstName = "Eve",
                lastName = "Brown",
                dateOfBirth = LocalDate.of(1995, 8, 19)
            )
        )

        // Save all mock users to the database
        userRepository.saveAll(users)
    }
}