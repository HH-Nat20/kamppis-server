package nat20.kamppisserver.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {

    private val key = "b8c485dc1b1db98ab477d6028a258609d729a18bda824d11d44e50a63b935e50" // TODO: Replace with proper secret in env

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
            .addFilterBefore(JwtAuthenticationFilter(key), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
