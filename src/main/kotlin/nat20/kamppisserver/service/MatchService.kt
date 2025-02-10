package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.MatchRequest
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class MatchService(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun addUserToMatch(matchId: Long, userId: Long): Match {
        val match = matchRepository.findById(matchId).orElseThrow {
            throw EntityNotFoundException("Match not found")
        }
        val user = userRepository.findById(userId).orElseThrow {
            throw EntityNotFoundException("User not found")
        }

        match.addUser(user)
        return matchRepository.save(match)
    }

    @Transactional
    fun removeUserFromMatch(matchId: Long, userId: Long): Match {
        val match = matchRepository.findById(matchId).orElseThrow {
            throw EntityNotFoundException("Match not found")
        }
        val user = userRepository.findById(userId).orElseThrow {
            throw EntityNotFoundException("User not found")
        }

        match.removeUser(user)
        return matchRepository.save(match)
    }

    fun findAll(): MutableIterable<Match> {
        return matchRepository.findAll()
    }

    fun findAllForUser(userId: Long): MutableIterable<Match> {
        return matchRepository.findAllByUserId(userId).toMutableList()
    }

    fun findOne(matchId: Long): Match? {
        return matchRepository.findByIdOrNull(matchId)
    }

    @Transactional
    fun createMatch(matchRequest: MatchRequest): Match {
        val users = userRepository.findAllById(matchRequest.userIds).toMutableSet()
        if (users.size < 2) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "One or more users not found")
        }

        val match = Match(users = users)
        return matchRepository.save(match)
    }

    // Overloaded method for internal use
    @Transactional
    fun createMatch(user1: User, user2: User): Match {
        val users = mutableSetOf(user1, user2)
        return matchRepository.save(Match(users = users))
    }
}