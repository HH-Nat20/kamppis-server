package nat20.kamppisserver.service

import nat20.kamppisserver.domain.RoomProfileInvite
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.repository.RoomProfileInviteRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class InviteService(
    private val roomProfileInviteRepository: RoomProfileInviteRepository,
    private val roomProfileRepository: RoomProfileRepository,
    private val userRepository: UserRepository
) {

    fun findInviteByRoomProfileId(roomProfileId: Long): RoomProfileInvite? {
        return roomProfileInviteRepository.findActiveInviteByRoomProfileId(roomProfileId, LocalDateTime.now())
    }

    fun generateRoomInviteToken(): String {
        val charPool: List<Char> = ('A'..'Z') + ('0'..'9')
        val roomInviteToken: String =
            (1..8)
            .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
            .joinToString("")
        return roomInviteToken
    }

    fun generateAndSaveRoomInvite(roomProfileId: Long): RoomProfileInvite {
        val roomProfileInvite = RoomProfileInvite(
            roomProfileId = roomProfileId,
            roomProfileInviteToken = generateRoomInviteToken(),
            expiresAt = LocalDateTime.now().plusSeconds(30),
        )

        roomProfileInviteRepository.save(roomProfileInvite)

        return roomProfileInvite
    }

    fun generateRoomProfileRequest(roomProfileId: Long, userId: Long): RoomProfileRequest {
        val roomProfile = roomProfileRepository.findById(roomProfileId).get()
        roomProfile.users.add(userRepository.findById(userId).get())

        return roomProfile.toRoomProfileRequest()
    }
}