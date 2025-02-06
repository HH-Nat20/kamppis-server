package nat20.kamppisserver.service

import org.springframework.stereotype.Service

import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserProfileRepository

@Service
class QueryService(private val userRepository: UserRepository, private val userProfileRepository: UserProfileRepository) {

    fun findUsersThatMeetCriteria(id: Long): MutableIterable<User> {

        /* We first find user's user profile by user's id
        * From the profile, we set user's search criteria to individual variables
        * Finally, we pass these variables to the SQL query in UserRepository*/

        val userProfile: UserProfile = userProfileRepository.findByUserId(id).get()
        val minAgePreference: Int? = userProfile.minAgePreference
        val maxAgePreference: Int? = userProfile.maxAgePreference

        return userRepository.findUsersThatMeetCriteria(id, minAgePreference, maxAgePreference)
    }
}
