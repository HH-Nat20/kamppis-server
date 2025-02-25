package exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException){
        throw ex
    }

    @ExceptionHandler(DuplicateSwipeException::class)
    fun handleDuplicateSwipe(ex: DuplicateSwipeException){
        throw ex
    }

    @ExceptionHandler(DuplicateMatchException::class)
    fun handleDuplicateMatch(ex: DuplicateMatchException) {
        throw ex
    }

    @ExceptionHandler(InvalidRequestException::class)
    fun handleInvalidRequest(ex: InvalidRequestException) {
        throw ex
    }
}