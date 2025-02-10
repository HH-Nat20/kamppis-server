package nat20.kamppisserver.api

import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.MatchRequest
import nat20.kamppisserver.service.MatchService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/match")
class MatchController(private val service: MatchService) {

    @GetMapping("/")
    fun findAll(): MutableIterable<Match> = service.findAll()

    @GetMapping("/{id}")
    fun findMatchById(@PathVariable id: Long) = service.findOne(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This Match does not exist")

    @GetMapping
    fun findAllForUser(@RequestParam userId: Long) = service.findAllForUser(userId)

    @PostMapping
    fun addMatch(@RequestBody request: MatchRequest): ResponseEntity<Match> {
        val savedMatch = service.createMatch(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMatch)
    }
}