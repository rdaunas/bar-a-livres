package fr.eni.baralivre.back.security;

import fr.eni.baralivre.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {


    private final  AuthEntryPoint unauthorizedHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPoint unauthorizedHandler) {

        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthFilter authenticationJwtTokenFilter() {
        return new AuthFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.cors() TODO ADD CORS HANDLIG
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(unauthorizedHandler)
                )
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

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
