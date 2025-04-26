package nat20.kamppisserver.service

import exception.DuplicateMatchException
import exception.InvalidRequestException
import exception.EntityNotFoundException
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class MatchService(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun addUserToMatch(matchId: Long, userId: Long): MatchDTO {
        val match = matchRepository.findById(matchId).orElseThrow {
            throw EntityNotFoundException("Match not found")
        }
        val user = userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User not found")

        match.addUser(user)
        return matchRepository.save(match).toDTO()
    }

    @Transactional
    fun removeUserFromMatch(matchId: Long, userId: Long): MatchDTO? {
        val match = matchRepository.findById(matchId).orElseThrow {
            throw EntityNotFoundException("Match not found")
        }
        val user = userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User not found")

        // If more than two users in the match, remove user
        if (match.users.size > 2) {
            match.removeUser(user)
        } else {
            // A match with a single user makes no sense, so we delete it
            matchRepository.delete(match)
            return null
        }

        return matchRepository.save(match).toDTO()
    }

    fun findAll(): List<MatchDTO> {
        return matchRepository.findAll().map { it.toDTO() }
    }

    fun findAllForUser(userId: Long): List<MatchDTO> {
        if (userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE) == null) throw EntityNotFoundException(
            "User with ID $userId not found"
        )
        return matchRepository.findAllByUserId(userId).map { it.toDTO() }
    }

    fun findUserProfilesThatMatchWithUser(userId: Long): List<UserProfileDTO>
    {
        if (userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE) == null) throw EntityNotFoundException(
            "User with ID $userId not found"
        )
        return matchRepository.findUserProfilesThatMatchWithUser(userId).map { it.toDTO() }
    }

    fun findOne(matchId: Long): MatchDTO? {
        if (!matchRepository.existsById(matchId)) throw EntityNotFoundException("Match with ID $matchId not found")
        return matchRepository.findByIdOrNull(matchId)?.toDTO()
    }

    @Transactional
    fun createMatch(@Valid matchRequest: MatchRequest): MatchDTO {
        val users = userRepository.findAllByIdAndStatus(matchRequest.userIds, UserStatus.ACTIVE).toMutableSet()
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
        return matchRepository.save(match).toDTO()
    }

    // Overloaded method for internal use
    @Transactional
    fun createMatch(users: Set<User>): MatchDTO {
        if (users.size < 2) {
            throw InvalidRequestException("A match must have at least two users")
        }

        // Check for an existing match with the same users
        val existingMatch = matchRepository.findExactMatch(users.mapNotNull { it.id }.toSet(), users.size)
        if (existingMatch.isNotEmpty()) {
            throw DuplicateMatchException("This match already exists")
        }

        return matchRepository.save(Match(users = users.toMutableSet())).toDTO()
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