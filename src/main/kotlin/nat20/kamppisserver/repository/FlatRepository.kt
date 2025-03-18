package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.Flat
import org.springframework.data.jpa.repository.JpaRepository

interface FlatRepository : JpaRepository<Flat, Long> {
}