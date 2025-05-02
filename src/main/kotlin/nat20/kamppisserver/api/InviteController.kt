package nat20.kamppisserver.api

import nat20.kamppisserver.domain.InviteResponse
import nat20.kamppisserver.service.InviteService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/invites")
class InviteController(
    private val inviteService: InviteService,
) {

    @PostMapping("/generate-invitetoken/{roomProfileId}")
    fun addRoomProfileInvite(@PathVariable roomProfileId: Long): ResponseEntity<InviteResponse> {
        val inviteResponse = inviteService.createInviteOrReturnExistingInvite(roomProfileId)
        return if (inviteResponse.message?.startsWith("Invite already exists") == true) {
            ResponseEntity(inviteResponse, HttpStatus.CONFLICT)
        } else {
            ResponseEntity(inviteResponse, HttpStatus.OK)
        }
    }

    @PutMapping("/join/{inviteToken}")
    fun joinRoom(@PathVariable inviteToken: String, @RequestHeader(HttpHeaders.AUTHORIZATION) authToken: String): ResponseEntity<InviteResponse> {
        val inviteResponse = inviteService.joinRoom(inviteToken, authToken)
        return if (inviteResponse.message?.startsWith("Invite code") == true) {
            ResponseEntity(inviteResponse, HttpStatus.GONE)
        } else if (inviteResponse.message?.startsWith("User already added") == true) {
            return ResponseEntity(inviteResponse, HttpStatus.CONFLICT)
        } else {
            ResponseEntity(inviteResponse, HttpStatus.OK)
        }

    }
}