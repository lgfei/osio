package com.lgfei.osio.auth.config;

import com.lgfei.osio.starter.core.service.OsioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    private OsioService osioService;

    public SecurityConfig(OsioService osioService){
        this.osioService = osioService;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/actuator/**",
                                "/assets/**",
                                "/webjars/**",
                                "/oauth2/**",
                                "/login").permitAll()
                        .anyRequest().authenticated()
                )
                //.formLogin(withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage(osioService.getGatewayUrl() + "/auth/login")
                        .defaultSuccessUrl(osioService.getGatewayUrl() + "/auth/home")
                        .permitAll()
                )
                .csrf().disable().build();
    }

    @Bean
    UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("test")
                .password("test")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}