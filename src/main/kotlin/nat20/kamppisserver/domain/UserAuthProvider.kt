package nat20.kamppisserver.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import nat20.kamppisserver.domain.enums.Provider
import java.time.LocalDateTime

@Entity
@Table(name = "user_auth_providers", uniqueConstraints = [
    UniqueConstraint(columnNames = ["provider", "provider_user_id"]) // Prevent duplicate provider-user ID
])
class UserAuthProvider(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Provider cannot be null")
    var provider: Provider,

    @Column(name = "provider_user_id",nullable = false)
    var providerUserId: Long,

    @PastOrPresent(message = "Creation date cannot be in the future.")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    ) {
}