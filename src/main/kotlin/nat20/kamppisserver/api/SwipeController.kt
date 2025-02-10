package nat20.kamppisserver.api

import nat20.kamppisserver.domain.Swipe
import nat20.kamppisserver.domain.SwipeRequest
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.service.SwipeService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/swipe")
class SwipeController(private val swipeService: SwipeService,
    private val userRepository: UserRepository
) {

    @GetMapping("/")
    fun findAll(): MutableIterable<Swipe> = swipeService.findAll()

    @PostMapping
    fun swipe(@RequestBody swipeRequest: SwipeRequest): ResponseEntity<Swipe> {
        val swipingUser: User = userRepository.findByIdOrNull(swipeRequest.swipingUserId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with id ${swipeRequest.swipingUserId} not found")
        val swipedUser: User = userRepository.findByIdOrNull(swipeRequest.swipedUserId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with id ${swipeRequest.swipedUserId} not found")
        val swipe = swipeService.swipe(
            swipingUser,
            swipedUser,
            isRightSwipe = swipeRequest.isRightSwipe
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(swipe)
    }


}