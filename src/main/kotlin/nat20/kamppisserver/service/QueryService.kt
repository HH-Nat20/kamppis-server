package nat20.kamppisserver.service

import exception.EntityNotFoundException
import nat20.kamppisserver.domain.*
import nat20.kamppisserver.domain.enums.ProfileStatus
import org.springframework.stereotype.Service
import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.*
import org.springframework.dao.EmptyResultDataAccessException
import java.time.LocalDate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * Service class for querying user profiles.
 */
@Service
class QueryService(
    private val userProfileRepository: UserProfileRepository,
    private val userRepository: UserRepository,
    private val roommatePreferenceRepository: RoommatePreferenceRepository,
    private val roomPreferenceRepository: RoomPreferenceRepository,
    private val roomProfileRepository: RoomProfileRepository,

) {

    /**
     * Finds the user's profile by user id.
     *
     * @param id the id of the user whose user profile is returned.
     * @throws EntityNotFoundException if no userId does not match any user
     * @return user's user profile.
     */
    fun findUserProfileByUserId(userId: Long): UserProfile? {
        return try {
            userProfileRepository.findByUserIdAndStatus(userId, ProfileStatus.ACTIVE)
        } catch (ex: EmptyResultDataAccessException) {
            throw EntityNotFoundException("UserId does not match to any user")
        }
    }

    /**
     * Finds a page of UserProfiles that match the user's own profile and search criteria.
     *
     * @param pageable pagination configuration.
     * @param userId the id of the user for whom matching profiles are returned.
     * @return a page of matching user profiles.
     */
    fun findUserProfilesThatMeetCriteria(pageable: Pageable, userId: Long): Page<UserProfileDTO> {
        /* We first find user's user profile by user's id
        * From the profile, we set user's search criteria to individual variables
        * Finally, we pass these variables to the SQL query in UserProfileRepository */

        val userProfile: UserProfile = userProfileRepository.findByUserIdAndStatus(userId, ProfileStatus.ACTIVE)
            ?: throw EntityNotFoundException("UserId does not match to any user")

        val roommatePreference: RoommatePreference = roommatePreferenceRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("UserId does not match to any user")

        val userProfileId: Long = userProfile.id!!
        val queryDate: LocalDate = LocalDate.now()
        val minAgePreference: Int? = roommatePreference.minAgePreference
        val maxAgePreference: Int? = roommatePreference.maxAgePreference
        val genderPreferences: List<String>? = roommatePreference.genderPreferences?.map { it.name }
        val locationPreferences: List<String>? = roommatePreference.locationPreferences?.map { it.name }

        val userProfileList: Page<UserProfile> = userProfileRepository.findUserProfilesThatMeetCriteria(
            pageable,
            userProfileId,
            queryDate,
            minAgePreference,
            maxAgePreference,
            genderPreferences,
            locationPreferences
        )

        val userProfileDTOList: Page<UserProfileDTO> = userProfileList.map { it.toDTO(includeUserSummary = true) }

        return userProfileDTOList
    }

    /**
     * Finds a page of RoomProfiles that match the user's search criteria and room profiles.
     *
     * @param pageable pagination configuration.
     * @param userId the id of the user for whom matching room profiles are returned.
     * @return a page of matching room profiles.
     */
    fun findRoomProfilesThatMeetCriteria(pageable: Pageable, userId: Long): Page<RoomProfileDTO> {
        /* We first find user's room preferences
        * From the preferences, we set user's search criteria to individual variables
        * Finally, we pass these variables to the SQL query in RoomProfileRepository */

        val userProfile: UserProfile = userProfileRepository.findByUserIdAndStatus(userId, ProfileStatus.ACTIVE)
            ?: throw EntityNotFoundException("UserId does not match to any user")

        val roomPreference: RoomPreference = roomPreferenceRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("UserId does not match to any user")

        val userProfileId: Long = userProfile.id!!
        val maxRent: Int? = roomPreference.maxRent
        val hasPrivateRoom: Boolean? = roomPreference.hasPrivateRoom
        val maxRoommates: Int? = roomPreference.maxRoommates
        val locationPreferences: List<String>? = roomPreference.locationPreferences?.map {it.name}

        val roomProfileList: Page<RoomProfile> = roomProfileRepository.findRoomProfilesThatMeetCriteria(
            pageable,
            userProfileId,
            maxRent,
            hasPrivateRoom,
            maxRoommates,
            locationPreferences
        )

        val roomProfileDTOList: Page<RoomProfileDTO> = roomProfileList.map {it.toDTO(includeUserSummary = true) }

        return roomProfileDTOList
    }

    /**
     * Finds a page of UserProfiles who have swiped right on a room profile.
     *
     * @param pageable pagination configuration.
     * @param roomProfileId the id of the room profile whose right swipers are returned.
     * @return a page of matching room profiles.
     */
    fun findUserProfilesThatHaveSwipedRoomProfile(pageable: Pageable, roomProfileId: Long): Page<UserProfileDTO> {
        val userProfileList: Page<UserProfile> = userProfileRepository.findUserProfilesWhoHaveSwipedRoomProfile(pageable, roomProfileId)
        val userProfileDTOList: Page<UserProfileDTO> = userProfileList.map { it.toDTO(includeUserSummary = true) }

        return userProfileDTOList
    }
}
