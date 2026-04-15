package fr.eni.baralivre.back.security;

import fr.eni.baralivre.back.repository.UserRepository;
import fr.eni.baralivre.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthFilter authenticationJwtTokenFilter;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(s ->
                        s.sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.GET, "/api/books").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/books/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/books/search").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/books").hasRole("LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT, "/api/books").hasRole("LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE, "/api/books").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET,"/api/loans/my").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/api/loans").hasRole("LIBRARIAN")
                                .requestMatchers(HttpMethod.POST, "/api/loans").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/api/loans/{id}/return").hasRole("LIBRARIAN")

                                .requestMatchers(HttpMethod.POST, "/api/books/{id}/ratings").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/ratings/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/ratings/{id}").hasRole("LIBRARIAN")

                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/signin").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll()
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
