package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Flat
import nat20.kamppisserver.domain.RoomProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FlatRepository : JpaRepository<Flat, Long> {

}