package nat20.kamppisserver.service

import io.mockk.*
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.setup.StandaloneSetup
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.MatchRequest
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import exception.EntityNotFoundException
import exception.DuplicateMatchException
import nat20.kamppisserver.domain.enums.Gender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.util.*

class MatchServiceTest {

    private lateinit var matchRepository: MatchRepository
    private lateinit var userRepository: UserRepository
    private lateinit var matchService: MatchService

    lateinit var match: Match
    lateinit var user1: User
    lateinit var user2: User
    lateinit var userProfile: UserProfile

    @BeforeEach
    fun setUp() {
        matchRepository = mockk()
        userRepository = mockk()
        matchService = MatchService(matchRepository, userRepository)

        StandaloneSetup.setup()
        match = StandaloneSetup.match
        user1 = StandaloneSetup.user1
        user2 = StandaloneSetup.user2
        userProfile = StandaloneSetup.userProfile
    }

    @Test
    fun `addUserToMatch should add user and return updated match`() {
        every { matchRepository.findById(match.id!!) } returns Optional.of(match)
        every { userRepository.findByIdAndStatus(user1.id!!, UserStatus.ACTIVE) } returns user1
        every { matchRepository.save(any()) } answers { firstArg() }

        val result = matchService.addUserToMatch(match.id!!, user1.id!!)

        assertThat(result).isNotNull
        assertThat(match.users).contains(user1)
    }

    @Test
    fun `removeUserFromMatch should remove user if more than 2 users`() {
        val user3 = User(
            firstName = "Deletable",
            lastName = "Doe",
            email = "deletable.doe@example.com",
            dateOfBirth = LocalDate.of(2000, 1, 1),
            gender = Gender.OTHER,
            id = 3L
        )

        match.users.add(user3)

        every { matchRepository.findById(match.id!!) } returns Optional.of(match)
        every { userRepository.findByIdAndStatus(user3.id!!, UserStatus.ACTIVE) } returns user3
        every { matchRepository.save(any()) } answers { firstArg() }

        val result = matchService.removeUserFromMatch(match.id!!, user3.id!!)

        assertThat(result).isNotNull
        assertThat(match.users).doesNotContain(user3)
    }

    @Test
    fun `removeUserFromMatch should delete match if only two users left`() {
        every { matchRepository.findById(match.id!!) } returns Optional.of(match)
        every { userRepository.findByIdAndStatus(user1.id!!, UserStatus.ACTIVE) } returns user1
        every { matchRepository.delete(match) } just Runs

        val result = matchService.removeUserFromMatch(match.id!!, user1.id!!)

        assertThat(result).isNull()
        verify { matchRepository.delete(match) }
    }

    @Test
    fun `findAll should return all matches`() {
        val matches = listOf(match)

        every { matchRepository.findAll() } returns matches

        val result = matchService.findAll()

        assertThat(result).hasSize(1)
    }

    @Test
    fun `findAllForUser should return matches for user`() {
        every { userRepository.findByIdAndStatus(user1.id!!, UserStatus.ACTIVE) } returns user1
        every { matchRepository.findAllByUserId(user1.id!!) } returns listOf(match)

        val result = matchService.findAllForUser(user1.id!!)

        assertThat(result).hasSize(1)
    }

    @Test
    fun `findUserProfilesThatMatchWithUser should return matching profiles`() {
        every { userRepository.findByIdAndStatus(user1.id!!, UserStatus.ACTIVE) } returns user1
        every { matchRepository.findUserProfilesThatMatchWithUser(user1.id!!) } returns mutableListOf(userProfile)

        val result = matchService.findUserProfilesThatMatchWithUser(user1.id!!)

        assertThat(result).hasSize(1)
    }

    @Test
    fun `findOne should return a match`() {
        every { matchRepository.existsById(match.id!!) } returns true
        every { matchRepository.findById(match.id!!) } returns Optional.of(match)

        val result = matchService.findOne(match.id!!)

        assertThat(result).isNotNull
    }

    @Test
    fun `findOne should throw if match does not exist`() {
        every { matchRepository.existsById(match.id!!) } returns false

        assertThrows<EntityNotFoundException> {
            matchService.findOne(match.id!!)
        }
    }

    @Test
    fun `createMatch with request should create new match`() {
        val users = listOf(user1, user2)

        every { userRepository.findAllByIdAndStatus(users.map { user -> user.id!! }.toSet(), UserStatus.ACTIVE) } returns users
        every { matchRepository.findExactMatch(any(), any()) } returns emptyList()
        every { matchRepository.save(any()) } answers { firstArg() }

        val matchRequest = MatchRequest(userIds = users.map { user -> user.id!! }.toSet())
        val result = matchService.createMatch(matchRequest)

        assertThat(result).isNotNull
    }

    @Test
    fun `createMatch with duplicate users should throw`() {
        val users = listOf(user1, user2)

        every { userRepository.findAllByIdAndStatus(users.map { user -> user.id!! }.toSet(), UserStatus.ACTIVE) } returns users
        every { matchRepository.findExactMatch(any(), any()) } returns listOf(match)

        val matchRequest = MatchRequest(userIds = users.map { user -> user.id!! }.toSet())

        assertThrows<DuplicateMatchException> {
            matchService.createMatch(matchRequest)
        }
    }

    @Test
    fun `createMatch with users should create new match`() {
        val users = setOf(user1, user2)

        every { matchRepository.findExactMatch(any(), any()) } returns emptyList()
        every { matchRepository.save(any()) } answers { firstArg() }

        val result = matchService.createMatch(users)

        assertThat(result).isNotNull
    }

    @Test
    fun `getUserIdsForMatch should return user ids`() {
        every { matchRepository.findById(match.id!!) } returns Optional.of(match)

        val result = matchService.getUserIdsForMatch(match.id!!)

        assertThat(result).isEqualTo(1L to 2L)
    }
}