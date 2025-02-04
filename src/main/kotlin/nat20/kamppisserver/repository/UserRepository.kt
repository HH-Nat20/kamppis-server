package nat20.kamppisserver.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.CrudRepository

import nat20.kamppisserver.domain.User

interface UserRepository : CrudRepository<User, Long> {
    /* Click to see SQL QUERY explanation
    * 1) SELECT * FROM \"users\"
    * -> We want to find all the users (and get all their user info) that match the criteria in the query,
    * -> so we select all users from the "users" table
    *
    * 2) WHERE
    * -> Here we specify the query filters
    *
    * 3) (\"id\" != :id)
    * -> We don't want to include the user who made the query in the results,
    * -> so we exclude them from the returned users
    *
    * More criteria and parameters will be added */

    @Query(value = "SELECT * FROM \"users\" WHERE (\"id\" != :id)", nativeQuery = true)
    fun findUsersThatMeetCriteria(@Param("id") id: Long): MutableIterable<User>

}