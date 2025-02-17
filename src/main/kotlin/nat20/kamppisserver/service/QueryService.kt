package nat20.kamppisserver.service

import org.springframework.stereotype.Service

import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository
import org.springframework.data.crossstore.ChangeSetPersister


@Service
class QueryService(private val userProfileRepository: UserProfileRepository) {

    /**
     * Finds the user's profile by user id.
     *
     * @param id the id of the user whose user profile is returned.
     * @return user's user profile.
     */
    fun findUserProfileByUserId(userId: Long?): UserProfile? {
        return userProfileRepository.findByUserId(userId)
    }

    /**
     * Finds a list of UserProfiles that match the user's own profile and search criteria.
     *
     * @param id the id of the user for whom matching profiles are returned.
     * @return a list of matching user profiles.
     */
    fun findUserProfilesThatMeetCriteria(userId: Long?): MutableIterable<UserProfile> {
        /* We first find user's user profile by user's id
        * From the profile, we set user's search criteria to individual variables
        * Finally, we pass these variables to the SQL query in UserProfileRepository */

        val userProfile: UserProfile? = findUserProfileByUserId(userId);

        val minAgePreference: Int? = userProfile?.minAgePreference
        val maxAgePreference: Int? = userProfile?.maxAgePreference

        return userProfileRepository.findUserProfilesThatMeetCriteria(userId, minAgePreference, maxAgePreference)
    }
}
