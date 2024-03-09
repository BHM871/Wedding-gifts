package com.example.wedding_gifts.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(crsf -> crsf.disable())
            .cors(cors -> cors.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.DELETE, "/account/delete/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/account/update/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/gift/delete/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/gift/create/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/gift/update/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/image/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/image/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/image/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/payment/{}").authenticated()
                .requestMatchers(HttpMethod.GET, "/payment/paid/**").authenticated()
                .anyRequest().permitAll()
            )
            .logout(logou ->
                logou.deleteCookies("remove")
                    .invalidateHttpSession(false)
                    .logoutUrl("/auth/logout")
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
