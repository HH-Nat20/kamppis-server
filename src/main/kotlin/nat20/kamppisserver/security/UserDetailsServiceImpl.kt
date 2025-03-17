package nat20.kamppisserver.security

import nat20.kamppisserver.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userService: UserService) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userService.findActiveUserByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")

        return UserDetailsImplementation(user.email)
    }
}