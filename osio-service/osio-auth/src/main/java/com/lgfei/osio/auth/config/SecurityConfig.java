package com.lgfei.osio.auth.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.lgfei.osio.auth.service.UserService;

/**
 * @author lgfei
 * @date 2025/7/13 20:45
 */
@Configuration
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http,
            AuthenticationProvider ldapAuthenticationProvider,
            AuthenticationManager authenticationManager) throws Exception
    {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/page/login", "/login", "/assets/**", "/static/**").permitAll()
                .anyRequest().authenticated());
        http.formLogin(form -> form
                .loginPage("/page/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/page/home", true)
                .failureUrl("/page/login?error=true")
                .permitAll()
        );
        http.authenticationManager(authenticationManager);
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

   @Bean
   public AuthenticationProvider customAuthenticationProvider() {
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setUserDetailsService(userService);
       provider.setPasswordEncoder(passwordEncoder());
       return provider;
   }

   @Bean
   public AuthenticationProvider inMemoryAuthenticationProvider() {
       UserDetails user = User.withUsername("user").password("123").roles("USER").build();
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setUserDetailsService(new InMemoryUserDetailsManager(user));
       provider.setPasswordEncoder(passwordEncoder());
       return provider;
   }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationProvider ldapAuthenticationProvider,
            AuthenticationProvider inMemoryAuthenticationProvider,
            AuthenticationProvider customAuthenticationProvider
    ) {
        return new ProviderManager(
                List.of(
                        ldapAuthenticationProvider,
                        inMemoryAuthenticationProvider,
                        customAuthenticationProvider
                )
        );
    }
}

