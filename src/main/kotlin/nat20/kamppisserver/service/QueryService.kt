package nat20.kamppisserver.service

import exception.EntityNotFoundException
import org.springframework.stereotype.Service

import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.UserProfileDTO
import nat20.kamppisserver.domain.toUserProfileDTO
import nat20.kamppisserver.repository.UserProfileRepository
import org.springframework.dao.EmptyResultDataAccessException
import java.time.LocalDate


@Service
class QueryService(private val userProfileRepository: UserProfileRepository) {

    /**
     * Finds the user's profile by user id.
     *
     * @param id the id of the user whose user profile is returned.
     * @throws EntityNotFoundException if no userId does not match any user
     * @return user's user profile.
     */
    fun findUserProfileByUserId(userId: Long?): UserProfile? {
        return try {
            userProfileRepository.findByUserId(userId)
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
    fun findUserProfilesThatMeetCriteria(userId: Long?): MutableList<UserProfileDTO> {
        /* We first find user's user profile by user's id
        * From the profile, we set user's search criteria to individual variables
        * Finally, we pass these variables to the SQL query in UserProfileRepository */

        val userProfile: UserProfile? = findUserProfileByUserId(userId)

        //TODO: val queryDate: LocalDate = LocalDate.now()
        val queryDate: LocalDate = LocalDate.of(2025, 2, 21)
        val minAgePreference: Int? = userProfile?.minAgePreference
        val maxAgePreference: Int? = userProfile?.maxAgePreference
        val preferredGenders: List<String> = userProfile?.preferredGenders!!.map { it.name }
        val preferredLocations: List<String> = userProfile.preferredLocations!!.map { it.name }

        val userProfileList: MutableList<UserProfile> = userProfileRepository.findUserProfilesThatMeetCriteria(
            userId,
            queryDate,
            minAgePreference,
            maxAgePreference,
            preferredGenders,
            preferredLocations
        )
        val userProfileDTOList: MutableList<UserProfileDTO> = userProfileList.map {toUserProfileDTO(it)}.toMutableList();

        return userProfileDTOList;
    }
}
