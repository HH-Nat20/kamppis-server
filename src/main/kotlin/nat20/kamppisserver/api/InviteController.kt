package nat20.kamppisserver.api

import nat20.kamppisserver.domain.InviteResponse
import nat20.kamppisserver.domain.RoomProfileInvite
import nat20.kamppisserver.domain.toInviteResponse
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.repository.RoomProfileInviteRepository
import nat20.kamppisserver.repository.RoomProfileRepository
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.service.InviteService
import nat20.kamppisserver.service.RoomProfileService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/invites")
class InviteController(
    private val inviteService: InviteService,
    private val roomProfileService: RoomProfileService,
    private val roomProfileInviteRepository: RoomProfileInviteRepository,
    private val userRepository: UserRepository,
    private val roomProfileRepository: RoomProfileRepository
) {

    @PostMapping("/generate-invitetoken/{roomProfileId}")
    fun addRoomProfileInvite(@PathVariable roomProfileId: Long): ResponseEntity<InviteResponse> {
        var invite: RoomProfileInvite? = inviteService.findInviteByRoomProfileId(roomProfileId)

        // Check if invite already exists
        if (invite != null) {
            val inviteResponse = toInviteResponse(invite)
            inviteResponse.message = "Invite already exists, use code ${inviteResponse.inviteToken}"
            return ResponseEntity(inviteResponse, HttpStatus.CONFLICT)
        }

        invite = inviteService.generateAndSaveRoomInvite(roomProfileId)
        val inviteJoinResponse = toInviteResponse(invite)
        inviteJoinResponse.message = "Invite created successfully!"

        return ResponseEntity(inviteJoinResponse, HttpStatus.OK)
    }

    @PutMapping("/join/{inviteToken}")
    fun joinRoom(@PathVariable inviteToken: String, @RequestHeader(HttpHeaders.AUTHORIZATION) authToken: String): ResponseEntity<InviteResponse> {
        val invite = roomProfileInviteRepository.findInviteByInviteToken(inviteToken)
        val inviteResponse = toInviteResponse(invite)

        // Check if invite is expired
        if (invite.expiresAt.isBefore(LocalDateTime.now())) {
            inviteResponse.message = "Invite code ${invite.roomProfileInviteToken} has expired"

            return ResponseEntity(inviteResponse, HttpStatus.GONE)
        }

        //Get userId from JWT token
        val token = authToken.removePrefix("Bearer ").trim()
        val email = JwtUtils.validateTokenAndGetEmail(token)
        val userIdAddedToRoom = userRepository.findByEmail(email!!)!!.id!!

        val roomProfileId = invite.roomProfileId

        // Check that user is not already added to the room
        if (roomProfileService.findUsersRoomProfiles(roomProfileId, userIdAddedToRoom).isNullOrEmpty()) {
            val updatedRoom = inviteService.generateRoomProfileRequest(roomProfileId, userIdAddedToRoom)

            roomProfileService.update(updatedRoom, roomProfileId)
            inviteResponse.message = "Room joined succesfully"

            return ResponseEntity(inviteResponse, HttpStatus.OK)
        }

        inviteResponse.message = "User already added to the room"

        return ResponseEntity(inviteResponse, HttpStatus.CONFLICT)
    }
}