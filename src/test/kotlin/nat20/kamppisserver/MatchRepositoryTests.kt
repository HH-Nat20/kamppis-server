package nat20.kamppisserver

import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertNotEquals

@DataJpaTest
class MatchRepositoryTests @Autowired constructor(
    val userRepository: UserRepository,
    val matchRepository: MatchRepository
){
    @Test
    fun `updatedAt should change when adding a user to match`() {
        val match = Match()
        matchRepository.save(match)

        val oldUpdatedAt = match.updatedAt

        val bobJohnson = User(
            email = "bob.johnson@example.com",
            firstName = "Bob",
            lastName = "Johnson",
            dateOfBirth = LocalDate.of(1985, 11, 22)
        )

        userRepository.save(bobJohnson)

        Thread.sleep(1000)
        match.addUser(userRepository.findById(1L).get())
        matchRepository.save(match)

        val updatedMatch = matchRepository.findById(match.id!!).get()
        assertNotEquals(oldUpdatedAt, updatedMatch.updatedAt)
    }
}