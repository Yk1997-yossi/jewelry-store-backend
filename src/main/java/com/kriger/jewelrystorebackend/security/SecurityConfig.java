package com.kriger.jewelrystorebackend.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request,response,exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                        .accessDeniedHandler((request,response,exception) -> response.setStatus(HttpServletResponse.SC_FORBIDDEN))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/customers/register","/api/customers/login","/api/admins/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/products/**","/api/categories/**","/api/productVariant/**","/api/media/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/products/**","/api/categories/**","/api/productVariant/**","/api/media/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/products/**","/api/categories/**","/api/productVariant/**","/api/media/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/products/**","/api/categories/**","/api/productVariant/**","/api/media/**").hasRole("ADMIN")
                        .requestMatchers("/api/admins/**").hasRole("ADMIN")
                        .requestMatchers("/api/customers/me","/api/customers/me/**","/api/addresses","/api/addresses/**","/api/shoppingCart","/api/shoppingCart/**").hasRole("CUSTOMER")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(List.of("Content-Type","Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**",configuration);
        return source;
    }
}