package nat20.kamppisserver.api

import nat20.kamppisserver.domain.InviteResponse
import nat20.kamppisserver.service.InviteService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/invites")
class InviteController(
    private val inviteService: InviteService,
) {

    @PostMapping("/generate-invitetoken/{roomProfileId}")
    fun addRoomProfileInvite(@PathVariable roomProfileId: Long): ResponseEntity<InviteResponse> {
        val inviteResponsePair = inviteService.createInviteOrReturnExistingInvite(roomProfileId)
        return ResponseEntity(inviteResponsePair.first, inviteResponsePair.second)
    }

    @PutMapping("/join/{inviteToken}")
    fun joinRoom(@PathVariable inviteToken: String, @RequestHeader(HttpHeaders.AUTHORIZATION) authToken: String): ResponseEntity<InviteResponse> {
        val inviteResponsePair = inviteService.joinRoom(inviteToken, authToken)
        return ResponseEntity(inviteResponsePair.first, inviteResponsePair.second)
    }
}