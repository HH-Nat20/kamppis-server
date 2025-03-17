package nat20.kamppisserver.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


// Useful if we want to implement user and admin roles
class UserDetailsImplementation(
    private val email: String
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList() // Modify if using roles

    override fun getPassword(): String = "WE_USE_TOKENS_INSTEAD"

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}