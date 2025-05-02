package nat20.kamppisserver.service

import exception.EntityNotFoundException
import nat20.kamppisserver.domain.InviteResponse
import nat20.kamppisserver.domain.toInviteResponse
import nat20.kamppisserver.domain.RoomProfileInvite
import nat20.kamppisserver.domain.RoomProfileRequest
import nat20.kamppisserver.repository.RoomProfileInviteRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.security.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class InviteService(
    private val roomProfileInviteRepository: RoomProfileInviteRepository,
    private val roomProfileRepository: RoomProfileRepository,
    private val userRepository: UserRepository,
    private val roomProfileService: RoomProfileService,
    private val jwtUtils: JwtUtils
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
            expiresAt = LocalDateTime.now().plusDays(1),
        )

        roomProfileInviteRepository.save(roomProfileInvite)

        return roomProfileInvite
    }

    fun generateRoomProfileRequest(roomProfileId: Long, userId: Long): RoomProfileRequest {
        val roomProfile = roomProfileRepository.findById(roomProfileId).get()
        roomProfile.users.add(userRepository.findById(userId).get())

        return roomProfile.toRoomProfileRequest()
    }

    fun createInviteOrReturnExistingInvite(roomProfileId: Long): Pair<InviteResponse, HttpStatus> {
        val existingInvite = findInviteByRoomProfileId(roomProfileId)

        // Check if invite already exists
        return if (existingInvite != null) {
            val inviteResponse = toInviteResponse(existingInvite)
            inviteResponse.message = "Invite already exists, use code ${inviteResponse.inviteToken}"
            Pair(inviteResponse, HttpStatus.CONFLICT)
        } else {
            val newInvite = generateAndSaveRoomInvite(roomProfileId)
            val inviteResponse = toInviteResponse(newInvite)
            inviteResponse.message = "Invite created successfully!"
            Pair(inviteResponse, HttpStatus.OK)
        }
    }

    fun joinRoom(inviteToken: String, authToken: String): Pair<InviteResponse, HttpStatus> {
        val invite = roomProfileInviteRepository.findInviteByInviteToken(inviteToken)
            ?: throw EntityNotFoundException("Invite with token $inviteToken not found")

        // Get userId from JWT token
        val token = authToken.removePrefix("Bearer ").trim()
        val email = jwtUtils.validateTokenAndGetEmail(token)
        val userIdAddedToRoom = userRepository.findByEmail(email!!)!!.id!!
        val roomProfileId = invite.roomProfileId

        return if (invite.expiresAt.isBefore(LocalDateTime.now())) {
            // Check if invite is expired
            val inviteResponse = toInviteResponse(invite)
            inviteResponse.message = "Invite code ${invite.roomProfileInviteToken} has expired"
            Pair(inviteResponse, HttpStatus.GONE)
        } else if (roomProfileService.findUsersRoomProfiles(roomProfileId, userIdAddedToRoom).isNullOrEmpty()) {
            val updatedRoom = generateRoomProfileRequest(roomProfileId, userIdAddedToRoom)
            roomProfileService.update(updatedRoom, roomProfileId)
            val inviteResponse = toInviteResponse(invite)
            inviteResponse.message = "Room joined successfully"
            Pair(inviteResponse, HttpStatus.OK)
        } else {
            val inviteResponse = toInviteResponse(invite)
            inviteResponse.message = "User already added to the room"
            Pair(inviteResponse, HttpStatus.CONFLICT)
        }
    }
}