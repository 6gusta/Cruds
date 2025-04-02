package tela.login.login.securaty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa CSRF para simplificar
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**", "/login", "/h2-console/**").permitAll() // Libera estas rotas
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/user/**").hasAuthority("USER")  // Restrição para ADMIN
                .anyRequest().authenticated() // Exige autenticação para o restante
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Garante JWT sem sessões
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro JWT
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // Permite o H2 Console

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}