package nat20.kamppisserver.service

import io.mockk.*
import nat20.kamppisserver.domain.RoomProfile
import nat20.kamppisserver.domain.RoomProfileInvite
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.RoomProfileInviteRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.setup.StandaloneSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class InviteServiceTest {

    private lateinit var roomProfileInviteRepository: RoomProfileInviteRepository
    private lateinit var roomProfileRepository: RoomProfileRepository
    private lateinit var userRepository: UserRepository
    private lateinit var inviteService: InviteService

    lateinit var user: User
    lateinit var roomProfile: RoomProfile
    lateinit var invite: RoomProfileInvite

    @BeforeEach
    fun setUp() {
        roomProfileInviteRepository = mockk()
        roomProfileRepository = mockk()
        userRepository = mockk()
        inviteService = InviteService(roomProfileInviteRepository, roomProfileRepository, userRepository)

        StandaloneSetup.setup()
        user = StandaloneSetup.user1
        roomProfile = StandaloneSetup.roomProfile
        invite = StandaloneSetup.invite
    }

    @Test
    fun `findInviteByRoomProfileId should return active invite`() {
        every { roomProfileInviteRepository.findActiveInviteByRoomProfileId(roomProfile.id!!, any()) } returns invite

        val result = inviteService.findInviteByRoomProfileId(roomProfile.id!!)

        assertThat(result).isEqualTo(invite)
        verify { roomProfileInviteRepository.findActiveInviteByRoomProfileId(roomProfile.id!!, any()) }
    }

    @Test
    fun `generateRoomInviteToken should create 8 character token`() {
        val token = inviteService.generateRoomInviteToken()

        assertThat(token).hasSize(8)
        assertThat(token.all { it.isUpperCase() || it.isDigit() }).isTrue()
    }

    @Test
    fun `generateAndSaveRoomInvite should save and return invite`() {
        every { roomProfileInviteRepository.save(any()) } answers { firstArg() }

        val result = inviteService.generateAndSaveRoomInvite(roomProfile.id!!)

        assertThat(result.roomProfileId).isEqualTo(roomProfile.id!!)
        assertThat(result.roomProfileInviteToken).hasSize(8)
        verify { roomProfileInviteRepository.save(any()) }
    }

    @Test
    fun `generateRoomProfileRequest should add user and return RoomProfileRequest`() {
        every { roomProfileRepository.findById(roomProfile.id!!) } returns Optional.of(roomProfile)
        every { userRepository.findById(user.id!!) } returns Optional.of(user)

        val result = inviteService.generateRoomProfileRequest(roomProfile.id!!, user.id!!)

        assertThat(roomProfile.users).contains(user)
        assertThat(result).isNotNull
        verify { roomProfileRepository.findById(roomProfile.id!!) }
        verify { userRepository.findById(user.id!!) }
    }
}