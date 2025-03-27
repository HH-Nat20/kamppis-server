package nat20.kamppisserver.service

import exception.EntityNotFoundException
import nat20.kamppisserver.domain.*
import org.springframework.stereotype.Service

import nat20.kamppisserver.domain.enums.UserStatus
import nat20.kamppisserver.repository.*
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

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
            userProfileRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
        } catch (ex: EmptyResultDataAccessException) {
            throw EntityNotFoundException("UserId does not match to any user")
        }
    }

    /**
     * Finds a list of UserProfiles that match the user's own profile and search criteria.
     *
     * @param id the id of the user for whom matching profiles are returned.
     * @return a list of matching user profiles.
     */
    fun findUserProfilesThatMeetCriteria(userId: Long): MutableList<UserProfileDTO> {
        /* We first find user's user profile by user's id
        * From the profile, we set user's search criteria to individual variables
        * Finally, we pass these variables to the SQL query in UserProfileRepository */

        val roommatePreference: RoommatePreference = roommatePreferenceRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("UserId does not match to any user")

        val queryDate: LocalDate = LocalDate.now()
        val minAgePreference: Int = roommatePreference.minAgePreference
        val maxAgePreference: Int = roommatePreference.maxAgePreference
        val genderPreferences: List<String> = roommatePreference.genderPreferences!!.map { it.name }
        val locationPreferences: List<String> = roommatePreference.locationPreferences!!.map { it.name }

        val userProfileList: MutableList<UserProfile> = userProfileRepository.findUserProfilesThatMeetCriteria(
            userId,
            queryDate,
            minAgePreference,
            maxAgePreference,
            genderPreferences,
            locationPreferences
        ).toMutableList()
        val userProfileDTOList: MutableList<UserProfileDTO> = userProfileList.map { it.toDTO(includeUserSummary = true) }.toMutableList();

        return userProfileDTOList
    }

    fun findRoomProfilesThatMeetCriteria(userId: Long): MutableList<RoomProfileDTO> {
        /* We first find user's room preferences
        * From the preferences, we set user's search criteria to individual variables
        * Finally, we pass these variables to the SQL query in RoomProfileRepository */

        val roomPreference: RoomPreference = roomPreferenceRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
            ?: throw EntityNotFoundException("UserId does not match to any user")

        val maxRent: Int? = roomPreference.maxRent
        val hasPrivateRoom: Boolean? = roomPreference.hasPrivateRoom
        val maxRoommates: Int? = roomPreference.maxRoommates
        val locationPreferences: List<String>? = roomPreference.locationPreferences?.map {it.name}

        val roomProfileList: MutableList<RoomProfile> = roomProfileRepository.findRoomProfilesThatMeetCriteria(
            userId,
            maxRent,
            hasPrivateRoom,
            maxRoommates,
            locationPreferences
        )

        val roomProfileDTOList: MutableList<RoomProfileDTO> = roomProfileList.map {it.toDTO(includeUserSummary = true) }.toMutableList()

        return roomProfileDTOList
    }
}
