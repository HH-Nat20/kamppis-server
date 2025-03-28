package nat20.kamppisserver.service

import exception.DuplicateEmailException
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserDTO
import nat20.kamppisserver.domain.UserDataDTO
import nat20.kamppisserver.domain.UserPreferenceDTO
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

/**
 * Service class for User.
 */
@Service
@Validated
class UserService(private val userRepository: UserRepository,
                  private val userProfileRepository: UserProfileRepository,
                  private val roomProfileRepository: RoomProfileRepository,
                  private val roommatePreferenceRepository: RoommatePreferenceRepository,
                  private val roomPreferenceRepository: RoomPreferenceRepository
) {

    /**
     * Returns all active Users.
     *
     * @return all Users as DTOs.
     */
    fun findAll(): List<UserDTO> {
        val users = userRepository.findAllByStatus(UserStatus.ACTIVE)
        return users.map { it.toDTO() }
    }

    /**
     * Returns active User by id.
     *
     * @param id the id of the User to be returned.
     * @return corresponding UserDTO.
     */
    fun findById(id: Long): UserDTO {
        val user = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User with id $id not found")

        return user.toDTO()
    }

    /**
     * Finds active User by e-mail.
     *
     * @param email the e-mail to search for.
     * @return the corresponding UserDTO.
     */
    fun findActiveUserByEmail(email: String): UserDTO? {
        val user = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User with email $email not found")

        return user.toDTO()
    }

    /**
     * Find user preferences for active User.
     */
    fun getPreferences(id: Long): UserPreferenceDTO {
        val user = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User with id $id not found")

        val roomPreference = roomPreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)
        val roommatePreference = roommatePreferenceRepository.findByUserIdAndStatus(user.id!!, UserStatus.ACTIVE)

        return UserPreferenceDTO(
            roomPreference = roomPreference?.toRoomPreferenceDTO(),
            roommatePreference = roommatePreference?.toRoommatePreferenceDTO(),
            id = user.id!!
        )
    }

    /**
     * Function for GDPR-compliant Copy of Information functionality. The User receives
     * a copy of
     * - profile information
     * - preferences and settings
     * - any data from related tables
     * - metadata (createdAt, latest update, etc.)
     * - logs
     * Does NOT return information that poses a security risk, e.g. IDs.
     */
     fun getCopyOfUserData(id: Long): UserDataDTO {
         val user = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
             ?: throw EntityNotFoundException("User with id $id not found")
        val userProfile = userProfileRepository.findByUserIdAndStatus(id, UserStatus.ACTIVE)
        val roomProfiles = roomProfileRepository.findByUserIdAndStatus(id, UserStatus.ACTIVE)
        val roommatePreference = roommatePreferenceRepository.findByUserIdAndStatus(id, UserStatus.ACTIVE)
        val roomPreference = roomPreferenceRepository.findByUserIdAndStatus(id, UserStatus.ACTIVE)
        return UserDataDTO(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            dateOfBirth = user.dateOfBirth,
            gender = user.gender,
            status = user.status,
            isOnline = user.isOnline,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            deletedAt = user.deletedAt,
            userProfile = userProfile?.toUserProfileDataDTO(),
            roomProfiles = roomProfiles.map { it?.toRoomProfileDataDTO() },
            roommatePreference = roommatePreference?.toRoommatePreferenceDataDTO(),
            roomPreference = roomPreference?.toRoomPreferenceDataDTO()
        )
     }

    /**
     * Creates new User.
     * TODO: Should not be able to create without redirecting to POST UserProfile!
     *
     * @param user the user to be created.
     * @return the created user.
     */
    fun add(@Valid user: User): UserDTO {
        // Check for all e-mails, even INACTIVE ones
        userRepository.findByEmail(user.email)
            ?.let { throw DuplicateEmailException("User with this email already exists") }

        val addedUser = userRepository.save(user)

        return addedUser.toDTO()
    }

    /**
     * Updates given active User.
     *
     * @param user the user to be updated.
     * @param id the id of the user to be updated.
     * @return the updated user.
     */
    fun update(@Valid user: UserDTO, id: Long): UserDTO {
        val updateUser = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User with id $id not found")
        // Check for all e-mails, even INACTIVE ones
        val existingUser = userRepository.findByEmail(updateUser.email)
        if (existingUser != null && existingUser.id != id) {
            throw DuplicateEmailException("User with this email already exists")
        }

        updateUser.firstName = user.firstName
        updateUser.lastName = user.lastName
        updateUser.email = user.email
        updateUser.gender = user.gender
        updateUser.updatedAt = LocalDateTime.now()

        val updatedUser = userRepository.save(updateUser)

        return updatedUser.toDTO()
    }

    /**
     * Soft deletes given User and subsequent UserProfile.
     *
     * @param id the id of the user to be deleted.
     */
    @Transactional
    fun delete(id: Long) {
        val deletedUser = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User with id $id not found")

        val deletedUserProfile = userProfileRepository.findByUserIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("User profile with id $id not found")

        deletedUser.deletedAt = LocalDateTime.now()
        deletedUserProfile.updatedAt = LocalDateTime.now()
        deletedUser.status = UserStatus.INACTIVE
        deletedUserProfile.deletedAt = LocalDateTime.now()
        deletedUserProfile.updatedAt = LocalDateTime.now()

        userRepository.save(deletedUser)
        userProfileRepository.save(deletedUserProfile)
    }

    /**
     * Restores deleted user.
     *
     * @param user the user to be restored.
     * @param id the id of the user to be restored.
     * @return the restored user.
     */
    @Transactional
    fun restore(id: Long): UserDTO {
        val restoreUser = userRepository.findByIdAndStatus(id, UserStatus.INACTIVE)
            ?: throw EntityNotFoundException("Deleted user with id $id not found")

        val restoredUserProfile = userProfileRepository.findByUserIdAndStatus(id, UserStatus.INACTIVE)
            ?: throw EntityNotFoundException("Deleted user profile with id $id not found")

        restoreUser.deletedAt = null
        restoreUser.updatedAt = LocalDateTime.now()
        restoreUser.status = UserStatus.ACTIVE
        restoredUserProfile.deletedAt = null
        restoredUserProfile.updatedAt = LocalDateTime.now()

        userProfileRepository.save(restoredUserProfile)
        val restoredUser = userRepository.save(restoreUser)

        return restoredUser.toDTO()
    }

}