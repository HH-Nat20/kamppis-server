package nat20.kamppisserver.api

import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.MatchRequest
import nat20.kamppisserver.service.MatchService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/matches")
class MatchController(private val service: MatchService) {

    @GetMapping("", "/")
    fun findAllForUser(@RequestParam(required = false) userId: Long?): ResponseEntity<MutableIterable<Match>> {
        return if (userId != null) {
            ResponseEntity.ok(service.findAllForUser(userId))
        } else {
            ResponseEntity.ok(service.findAll())
        }
    }

    @GetMapping("/profiles/{id}")
    fun findAllMatchesForUser(@PathVariable id: Long): ResponseEntity<MutableIterable<UserProfile>> {
        return ResponseEntity.ok(service.findUserProfilesThatMatchWithUser(id))
    }

    @GetMapping("/{id}")
    fun findMatchById(@PathVariable id: Long): ResponseEntity<Match> {
        return ResponseEntity.ok(service.findOne(id))
    }

    @PostMapping
    fun addMatch(@RequestBody request: MatchRequest): ResponseEntity<Match> {
        val savedMatch = service.createMatch(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMatch)
    }
}