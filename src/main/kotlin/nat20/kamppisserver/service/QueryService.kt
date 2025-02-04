package nat20.kamppisserver.service

import org.springframework.stereotype.Service
import com.fasterxml.jackson.databind.JsonNode

import nat20.kamppisserver.repository.UserRepository
import nat20.kamppisserver.domain.User

@Service
class QueryService(private val userRepository: UserRepository) {

    fun findUsersThatMeetCriteria(criteria: JsonNode): MutableIterable<User> {
        /* We first destruct the GET request body that holds the criteria to individual parameters
        * Then we pass these parameters to the SQL query
        * NOTE! Criteria in the request body must be destructed to individual search parameter variables!
        * -> val criteria: type = criteria["criteriaKeyInRequestBody"].asType()
        * NOTE! All individual search parameter variables must be given as function parameters below!*/

        val id: Long = criteria["searchMadeByUserId"].asLong()

        return userRepository.findUsersThatMeetCriteria(id)
    }
}