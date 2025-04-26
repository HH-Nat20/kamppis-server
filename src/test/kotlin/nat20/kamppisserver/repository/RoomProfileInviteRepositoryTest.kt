package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.setup.ContextSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomProfileInviteRepositoryTest @Autowired constructor(
    val roomProfileInviteRepository: RoomProfileInviteRepository,
    val roomPreferenceRepository: RoomPreferenceRepository,
    val roomProfileRepository: RoomProfileRepository,
    val flatRepository: FlatRepository,
    val userRepository: UserRepository,
    val userProfileRepository: UserProfileRepository,
    val roommatePreferenceRepository: RoommatePreferenceRepository
) {

    lateinit var user: User
    lateinit var flat: Flat
    lateinit var roomProfile: RoomProfile
    lateinit var contextSetup: ContextSetup

    @BeforeEach
    fun init() {
        contextSetup = ContextSetup(
            userRepository,
            userProfileRepository,
            flatRepository,
            roomProfileRepository,
            roomPreferenceRepository,
            roommatePreferenceRepository
        )
        contextSetup.setup()
        user = contextSetup.user
        flat = contextSetup.flat
        roomProfile = contextSetup.roomProfile
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