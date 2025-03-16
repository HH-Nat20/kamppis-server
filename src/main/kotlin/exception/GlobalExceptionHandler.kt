package exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException){
    }

    @ExceptionHandler(DuplicateSwipeException::class)
    fun handleDuplicateSwipe(ex: DuplicateSwipeException){
    }

    @ExceptionHandler(DuplicateMatchException::class)
    fun handleDuplicateMatch(ex: DuplicateMatchException) {
    }

    @ExceptionHandler(DuplicateEmailException::class)
    fun handleDuplicateEmail(ex: DuplicateEmailException) {
    }

    @ExceptionHandler(InvalidRequestException::class)
    fun handleInvalidRequest(ex: InvalidRequestException) {
    }
}