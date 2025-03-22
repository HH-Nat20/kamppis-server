package nat20.kamppisserver.api

import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.MatchService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/matches")
class MatchController(private val service: MatchService,
                      private val repository: UserRepository) {

    @GetMapping("", "/")
    fun findAllForUser(@RequestParam(required = false) userId: Long?): ResponseEntity<List<MatchDTO>> {
        return if (userId != null && repository.findByIdAndStatus(userId, UserStatus.ACTIVE) != null) {
            ResponseEntity.ok().body(service.findAllForUser(userId))
        } else {
            ResponseEntity.ok().body(service.findAll())
        }
    }

    @GetMapping("/profiles/{id}")
    fun findAllMatchesForUser(@PathVariable id: Long): ResponseEntity<List<UserProfileDTO>> {
        return ResponseEntity.ok().body(service.findUserProfilesThatMatchWithUser(id))
    }

    @GetMapping("/{id}")
    fun findMatchById(@PathVariable id: Long): ResponseEntity<MatchDTO> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findOne(id))
    }

    @PostMapping()
    fun addMatch(@RequestBody request: MatchRequest): ResponseEntity<MatchDTO> {
        val savedMatch = service.createMatch(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMatch)
    }
}