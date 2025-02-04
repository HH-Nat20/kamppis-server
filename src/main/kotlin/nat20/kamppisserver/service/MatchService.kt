package nat20.kamppisserver.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.stereotype.Service

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
}