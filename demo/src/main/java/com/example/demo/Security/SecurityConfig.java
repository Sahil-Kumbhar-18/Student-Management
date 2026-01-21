package com.example.demo.Security;

import com.example.demo.Security.JWT.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(req->req
                         .requestMatchers("/signup", "/login").permitAll()
                         .requestMatchers("/admin/**").hasRole("ADMIN")
                         .requestMatchers("/student/**").hasRole("STUDENT")
                         .requestMatchers("/teacher/**").hasRole("TEACHER")
                 )
                 .sessionManagement(session->session
                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
         http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

         return http.build();
     }
}
