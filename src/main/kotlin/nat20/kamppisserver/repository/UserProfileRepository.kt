package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Gender
import org.springframework.data.jpa.repository.JpaRepository
import nat20.kamppisserver.domain.UserProfile
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate


interface UserProfileRepository: JpaRepository<UserProfile, Long> {

    @Query(value = "SELECT * FROM \"user_profiles\" WHERE (\"user_id\" = :id)", nativeQuery = true)
    fun findByUserId(
        @Param("id") id: Long?): UserProfile

    /* Click to see SQL QUERY explanation
    * 1) SELECT * FROM \"user_profiles\"
    * -> We want to find all the user profiles (and all their relevant info according to the DTO) that match the criteria in the query
    * -> For this, we select all columns in the user profiles from the "user_profiles" table
    *
    * 2) WHERE
    * -> Here we specify the query filters
    *
    * 3) (\"id\" != :id)
    * -> We don't want to include the user who made the query in the results,
    * -> so we exclude them from the returned users
    *
    * 4) (DATEDIFF(YEAR, "date_of_birth", :queryDate) + CASE WHEN DATEADD(YEAR, DATEDIFF(YEAR, "date_of_birth", :queryDate), "date_of_birth") > :queryDate THEN -1 ELSE 0 END) BETWEEN COALESCE(:minAgePreference, 0) AND COALESCE(:maxAgePreference, 1000)
    * -> We calculate user's age based on their date of birth
    * -> We remove 1 year if the user's has not yet had their birthday this year
    * -> We include only those users who fit min and max age preferences (inclusive)
    * -> If user has not set any age preferences (i.e. null), values 0 and 1000 are used to include user profiles of all ages
    *
    * More criteria and parameters will be added */
    
    @Query(value= "SELECT * FROM \"user_profiles\" " +
            "WHERE (\"id\" != :id) " +
            "AND ((DATEDIFF(YEAR, \"date_of_birth\", :queryDate) + CASE WHEN DATEADD(YEAR, DATEDIFF(YEAR, \"date_of_birth\", :queryDate), \"date_of_birth\") > :queryDate THEN -1 ELSE 0 END) BETWEEN COALESCE(:minAgePreference, 0) AND COALESCE(:maxAgePreference, 1000)) " +
            "AND ((CAST(\"gender\" AS VARCHAR) IN (:preferredGenders)) OR ('NOT_IMPORTANT' IN (:preferredGenders)))",
        nativeQuery = true)
    fun findUserProfilesThatMeetCriteria(
        @Param("id") id: Long?,
        @Param("queryDate") queryDate: LocalDate,
        @Param("minAgePreference") minAgePreference: Int?,
        @Param("maxAgePreference") maxAgePreference: Int?,
        @Param("preferredGenders") preferredGenders: List<String>): MutableIterable<UserProfile>

}