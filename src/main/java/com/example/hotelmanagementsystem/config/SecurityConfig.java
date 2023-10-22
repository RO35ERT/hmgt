package com.example.hotelmanagementsystem.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

@EnableWebSecurity
@Configuration
@EnableScheduling
public class SecurityConfig {

    @Autowired
    private CustomLoginSuccessHandler successHandler;
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/registration/**","/css/**","/js/**").permitAll()
                        .requestMatchers("/login/**","/","/confirm","/confirmPage").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER")
                        .requestMatchers("/reception/**").hasAnyRole("RECEPTION")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER")
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout((log)->
                        log.logoutUrl("/logout")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/")
                                .permitAll()
                )
                .rememberMe(Customizer.withDefaults())
                ;
        return http.build();
    }
}
