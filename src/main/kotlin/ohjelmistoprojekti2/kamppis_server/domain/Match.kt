package ohjelmistoprojekti2.kamppis_server.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
class Match(
    @ManyToOne var user1: User,
    @ManyToOne var user2: User,
    var createdAt: LocalDateTime,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)