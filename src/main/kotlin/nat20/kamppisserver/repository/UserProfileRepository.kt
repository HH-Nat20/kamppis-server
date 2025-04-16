package nat20.kamppisserver.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.EntityGraph
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.ProfileStatus
import nat20.kamppisserver.domain.enums.UserStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface UserProfileRepository: JpaRepository<UserProfile, Long> {

    @EntityGraph(attributePaths = ["photos"])
    @Query("SELECT up FROM UserProfile up WHERE up.deletedAt IS NULL")
    fun findAllActive(): List<UserProfile>

    @Query("SELECT up FROM UserProfile up WHERE up.id = :id AND up.deletedAt IS NULL")
    fun findByIdActive(@Param("id") id: Long): UserProfile?

    @Query("SELECT up FROM UserProfile up WHERE up.user.id = :id AND up.status = :status")
    fun findByUserIdAndStatus(@Param("id") id: Long, @Param("status") status: ProfileStatus): UserProfile?

    /* Click to see SQL QUERY explanation
* 1) SELECT DISTINCT up.* FROM \"user_profiles\" up
* -> We want to find all distinct user profiles (and all their relevant info according to the DTO) that match the criteria in the query
* -> For this, we select all columns in the user profiles from the "user_profiles" table with the alias "up"
*
* 2) JOIN "user_profiles_genders" upg ON up."id" = upg."user_profile_id"
* -> Preferred genders are stored as a list of strings, converted from enums
* -> In Spring, this creates a new table, so we join the "user_profiles_genders" table with the alias "upg" to the "user_profiles" table
*
* 3) JOIN "user_profiles_locations" upl ON up."id" = upl."user_profile_id"
* -> Preferred locations are stored as a list of strings, converted from enums
* -> In Spring, this creates a new table, so we join the "user_profiles_locations" table with the alias "upl" to the "user_profiles" table
*
* 4) WHERE
* -> Here we specify the query filters
*
* 5) up."id" != :id
* -> We don't want to include the user who made the query in the results
* -> So we exclude them from the returned users
*
* 6) AND NOT EXISTS (SELECT 1 FROM "swipes" s WHERE s."swiping_user_id" = :id AND s."swiped_user_id" = up."id")
* -> We filter out the profiles that have already been swiped
* -> So the user's don't see profiles that they have already swiped
*
* 7) AND (DATEDIFF(YEAR, up."date_of_birth", :queryDate) + CASE WHEN DATEADD(YEAR, DATEDIFF(YEAR, up."date_of_birth", :queryDate), up."date_of_birth") > :queryDate THEN -1 ELSE 0 END) BETWEEN COALESCE(:minAgePreference, 0) AND COALESCE(:maxAgePreference, 1000)
* -> We calculate user's age based on their date of birth
* -> We remove 1 year if the user's has not yet had their birthday this year
* -> We include only those users who fit min and max age preferences (inclusive)
* -> If user has not set any age preferences (i.e. null), values 0 and 1000 are used to include user profiles of all ages
*
* 8) AND (up."gender" IN (:genderPreferences) OR 'NOT_IMPORTANT' IN (:genderPreferences))
* -> We check if the queried profiles' gender matches the user's list of preferred genders
* -> We also check if the user has no gender preferences (preferred gender set as 'NOT_IMPORTANT')
*
* 9) AND upl."location_preferences" IN (:locationPreferences)
* -> We check if any of the user profiles' preferred cities match the user's list of preferred cities
*
* More criteria and parameters will be added */

    @Query("""
    SELECT DISTINCT up.id, up.user_id, up.cleanliness, up.pets, u.gender, rpl.location_preferences, p.bio, p.status, p.created_at, p.updated_at, p.deleted_at
    FROM user_profiles up
    JOIN users u ON u.id = up.user_id
    JOIN roommate_preferences rp ON rp.user_id = u.id
    LEFT JOIN (
        SELECT rp.user_id, 
               ARRAY_AGG(rpl.location_preferences) AS location_preferences
        FROM roommate_preferences rp
        LEFT JOIN roommate_preferences_location rpl ON rpl.roommate_preferences_id = rp.id
        GROUP BY rp.user_id
    ) rpl ON rpl.user_id = u.id
    LEFT JOIN profiles p ON up.id = p.id  -- Join with profiles to get bio, status, etc.
    WHERE up.id != :userProfileId
    AND u.looking_for = 'OTHER_USER_PROFILES' -- Check and filter user profiles on what the user is looking for
    AND NOT EXISTS (
        SELECT 1
        FROM swipes s
        WHERE s.swiping_profile_id = :userProfileId
        AND s.swiped_profile_id = up.id
    )
    AND (EXTRACT(YEAR FROM AGE(:queryDate, u.date_of_birth)) BETWEEN COALESCE(:minAgePreference, 0) AND COALESCE(:maxAgePreference, 1000))
    AND (COALESCE(:genderPreferences) IS NULL OR u.gender IN (:genderPreferences))
   
    AND (COALESCE(:locationPreferences) IS NULL 
        OR rp.id NOT IN (
            SELECT rpl.roommate_preferences_id
            FROM roommate_preferences_location rpl
        )
        OR EXISTS (
            SELECT 1
            FROM UNNEST(rpl.location_preferences) AS location
            WHERE location IN (:locationPreferences)
        )
    )
    """, nativeQuery = true)
    fun findUserProfilesThatMeetCriteria(
        pageable: Pageable,
        @Param("userProfileId") userProfileId: Long?,
        @Param("queryDate") queryDate: LocalDate,
        @Param("minAgePreference") minAgePreference: Int?,
        @Param("maxAgePreference") maxAgePreference: Int?,
        @Param("genderPreferences") genderPreferences: List<String>?,
        @Param("locationPreferences") locationPreferences: List<String>?
    ): Page<UserProfile>

    @Query("""
        SELECT DISTINCT up.id, up.user_id, up.cleanliness, up.pets, u.gender, p.bio, p.status, p.created_at, p.updated_at, p.deleted_at
        FROM user_profiles up
        JOIN users u ON u.id = up.user_id
        JOIN swipes s ON up.id = s.swiping_profile_id
        JOIN room_profiles rp on rp.id = s.swiped_profile_id
        LEFT JOIN profiles p ON up.id = p.id
        WHERE s.swiped_profile_id = :roomProfileId
        AND s.is_right_swipe = TRUE
    """, nativeQuery = true)
    fun findUserProfilesWhoHaveSwipedRoomProfile(
        pageable: Pageable,
        @Param("roomProfileId") roomProfileId: Long
    ): Page<UserProfile>
}