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
                auth.requestMatchers("/api/login").permitAll() // Always allow login

                auth.requestMatchers("/api/login/protected").authenticated() // Maybe this works?

                auth.anyRequest().permitAll() // Allow all other requests for now
                // TODO: Apply authentication to all endpoints
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
