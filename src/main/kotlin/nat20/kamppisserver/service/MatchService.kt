package nat20.kamppisserver.service

import exception.DuplicateMatchException
import exception.InvalidRequestException
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
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

    fun findAll(): MutableIterable<Match> {
        return matchRepository.findAll()
    }

    fun findAllForUser(userId: Long): MutableIterable<Match> {
        if (!userRepository.existsById(userId)) throw EntityNotFoundException("USer with ID $userId not found")
        return matchRepository.findAllByUserId(userId).toMutableList()
    }

    fun findUserProfilesThatMatchWithUser(userId: Long): MutableIterable<UserProfile>
    {
        if (!userRepository.existsById(userId)) throw EntityNotFoundException("USer with ID $userId not found")
        return matchRepository.findUserProfilesThatMatchWithUser(userId)
    }

    fun findOne(matchId: Long): Match? {
        if (!matchRepository.existsById(matchId)) throw EntityNotFoundException("Match with ID $matchId not found")
        return matchRepository.findByIdOrNull(matchId)
    }

    @Transactional
    fun createMatch(matchRequest: MatchRequest): Match {
        val users = userRepository.findAllById(matchRequest.userIds).toMutableSet()
        if (users.size < 2) {
            throw InvalidRequestException("A match must have at least two unique users")
        }
        if (users.size != matchRequest.userIds.size) {
            throw EntityNotFoundException("One or more users not found")
        }

        // Check for an existing match
        val existingMatch = matchRepository.findExactMatch(users.mapNotNull { it.id }.toSet(), users.size)
        if (existingMatch.isNotEmpty()) {
            throw DuplicateMatchException("This match already exists")
        }

        val match = Match(users = users)
        return matchRepository.save(match)
    }

    // Overloaded method for internal use
    @Transactional
    fun createMatch(users: Set<User>): Match {
        if (users.size < 2) {
            throw InvalidRequestException("A match must have at least two users")
        }

        // Check for an existing match with the same users
        val existingMatch = matchRepository.findExactMatch(users.mapNotNull { it.id }.toSet(), users.size)
        if (existingMatch.isNotEmpty()) {
            throw DuplicateMatchException("This match already exists")
        }

        return matchRepository.save(Match(users = users.toMutableSet()))
    }

    /**
     * Finds User IDs for specific Match. Used particularly for chats.
     * @param matchId the match for which to check for users.
     * @return tuple with two user IDs.
     */
    fun getUserIdsForMatch(matchId: Long): Pair<Long, Long>? {
        val match = matchRepository.findById(matchId).orElse(null) ?: return null

        val users = match.users.toList()
        if (users.size != 2) {
            throw IllegalStateException("A match must have exactly two users to start a chat.")
        }

        return users[0].id!! to users[1].id!!
    }

}