package nat20.kamppisserver.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { } // Enable CORS
            .csrf { it.disable() } // Disable CSRF for development
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/api/login").permitAll() // Allow login for mock users
                auth.requestMatchers("/api/health").permitAll()
                auth.requestMatchers("/api/db-health").permitAll()
                auth.requestMatchers("/api/login/github").permitAll() // Allow fetching GitHub code
                auth.requestMatchers("/api/login/signup").permitAll() // Allow signup after fetching GitHub code

                auth.requestMatchers("/api/users").permitAll() // TODO: Remove "/api/users"
                auth.requestMatchers("/ws").permitAll()

                auth.requestMatchers("/api/login/protected").authenticated()

                auth.anyRequest().authenticated() // JWT token required for all other endpoints
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
