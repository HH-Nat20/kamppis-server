package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.FeedbackDTO
import nat20.kamppisserver.service.FeedbackService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/feedback")
@Validated
class FeedbackController(private val service: FeedbackService) {

    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<FeedbackDTO>> {
        return ResponseEntity.ok(service.findAll())
    }

    @PostMapping
    fun add(@Valid @RequestBody request: FeedbackDTO): ResponseEntity<FeedbackDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request))
    }
}