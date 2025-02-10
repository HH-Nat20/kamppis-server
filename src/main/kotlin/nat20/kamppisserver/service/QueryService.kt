package nat20.kamppisserver.service

import org.springframework.stereotype.Service

import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository

@Service
class QueryService(private val userProfileRepository: UserProfileRepository) {

    fun findUserProfilesThatMeetCriteria(id: Long): MutableIterable<UserProfile> {

        val userProfile: UserProfile = userProfileRepository.findByUserId(id);

        /* We first find user's user profile by user's id
        * From the profile, we set user's search criteria to individual variables
        * Finally, we pass these variables to the SQL query in UserProfileRepository*/

        val minAgePreference: Int? = userProfile.minAgePreference
        val maxAgePreference: Int? = userProfile.maxAgePreference

        return userProfileRepository.findUserProfilesThatMeetCriteria(id, minAgePreference, maxAgePreference)
    }
}
