package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Gender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomProfileInviteRepositoryTest @Autowired constructor(
    val roomProfileInviteRepository: RoomProfileInviteRepository,
    val roomProfileRepository: RoomProfileRepository,
    val flatRepository: FlatRepository,
    val userRepository: UserRepository
) {

    lateinit var user: User
    lateinit var flat: Flat
    lateinit var roomProfile: RoomProfile

    @BeforeEach
    fun setup() {
        user = userRepository.save(
            User(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                dateOfBirth = LocalDate.of(1980, 1, 1),
                gender = Gender.MALE
            )
        )

        flat = flatRepository.save(
            Flat(
                name = "Nice place",
                description = "Sunny",
                location = City.HELSINKI,
                totalRoommates = 3
            )
        )

        roomProfile = roomProfileRepository.save(
            RoomProfile(
                users = mutableListOf(user),
                flat = flat,
                rent = 500,
                isPrivateRoom = false,
                furnished = false,
                bio = "A cool place"
            )
        )
    }

    @Test
    fun `findActiveInviteByRoomProfileId returns invite when not expired`() {
        val invite = roomProfileInviteRepository.save(
            RoomProfileInvite(
                roomProfileId = roomProfile.id!!,
                roomProfileInviteToken = "abc123",
                expiresAt = LocalDateTime.now().plusHours(2)
            )
        )

        val result = roomProfileInviteRepository.findActiveInviteByRoomProfileId(
            roomProfile.id!!, LocalDateTime.now()
        )

        assertThat(result).isNotNull
        assertThat(result!!.id).isEqualTo(invite.id)
    }

    @Test
    fun `findActiveInviteByRoomProfileId returns null when invite is expired`() {
        roomProfileInviteRepository.save(
            RoomProfileInvite(
                roomProfileId = roomProfile.id!!,
                roomProfileInviteToken = "expiredToken",
                expiresAt = LocalDateTime.now().minusHours(1)
            )
        )

        val result = roomProfileInviteRepository.findActiveInviteByRoomProfileId(
            roomProfile.id!!, LocalDateTime.now()
        )

        assertThat(result).isNull()
    }

    @Test
    fun `findInviteByInviteToken returns invite when token matches`() {
        val invite = roomProfileInviteRepository.save(
            RoomProfileInvite(
                roomProfileId = roomProfile.id!!,
                roomProfileInviteToken = "uniqueToken",
                expiresAt = LocalDateTime.now().plusDays(1)
            )
        )

        val result = roomProfileInviteRepository.findInviteByInviteToken("uniqueToken")

        assertThat(result).isNotNull
        assertThat(result!!.id).isEqualTo(invite.id)
    }

    @Test
    fun `findInviteByInviteToken returns null for unknown token`() {
        val result = roomProfileInviteRepository.findInviteByInviteToken("nonexistent")
        assertThat(result).isNull()
    }
}