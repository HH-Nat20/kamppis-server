package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.service.SwipeService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/swipes")
@Validated
class SwipeController(
    private val swipeService: SwipeService,
) {

    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<SwipeDTO>> {
        val swipes = swipeService.findAll()
        return ResponseEntity.ok().body(swipes)
    }

    @PostMapping
    fun validateAndSwipe(@Valid @RequestBody swipeRequest: SwipeRequest, principal: Principal): ResponseEntity<SwipeResponse> {
        return swipeService.validateAndSwipe(swipeRequest, principal)
    }
}