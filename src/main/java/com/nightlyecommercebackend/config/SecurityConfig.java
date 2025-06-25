package com.nightlyecommercebackend.config;

import com.nightlyecommercebackend.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(customizer->customizer.disable())
            .authorizeHttpRequests(request-> request.anyRequest().authenticated()) // every api is authenticated
    //                .formLogin(Customizer.withDefaults()) // enables formlogin for entering userdetails to login and access apis
            .httpBasic(Customizer.withDefaults()) // enables basic authentication
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // this will make csrf to create for every time we reload.

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(MyUserDetailsService myUserDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        authProvider.setUserDetailsService(myUserDetailsService);
        return authProvider;
    }

}
