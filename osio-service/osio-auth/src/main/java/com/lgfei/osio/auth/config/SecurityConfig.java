package com.lgfei.osio.auth.config;

import com.lgfei.osio.auth.customsize.OsioAuthenticationFailureHandler;
import com.lgfei.osio.auth.customsize.OsioAuthenticationFilter;
import com.lgfei.osio.auth.customsize.OsioAuthenticationSuccessHandler;
import com.lgfei.osio.auth.customsize.OsioLogoutFilter;
import com.lgfei.osio.auth.customsize.OsioLogoutHandler;
import com.lgfei.osio.auth.customsize.OsioLogoutSuccessHandler;
import com.lgfei.osio.starter.core.service.OsioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OsioService osioService;

    public SecurityConfig(OsioService osioService){
        this.osioService = osioService;
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("root").password(passwordEncoder().encode("123456")).authorities("ROOT").build());
        manager.createUser(User.withUsername("manager").password(passwordEncoder().encode("123456")).authorities("MANAGER").build());
        manager.createUser(User.withUsername("user").password(passwordEncoder().encode("123456")).authorities("USER").build());

        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**","/assets/**","/webjars/**","/oauth/**","/public/**")
                .permitAll()
                //.anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(osioService.getBaseUrl() + "/public/login")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .csrf().disable();
    }

    @Bean
    public OsioAuthenticationFilter osioAuthenticationFilter() throws Exception {
        OsioAuthenticationFilter osioAuthenticationFilter = new OsioAuthenticationFilter();
        osioAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        osioAuthenticationFilter.setAuthenticationFailureHandler(new OsioAuthenticationFailureHandler(osioService));
        osioAuthenticationFilter.setAuthenticationSuccessHandler(new OsioAuthenticationSuccessHandler(osioService));
        osioAuthenticationFilter.setFilterProcessesUrl("/public/doLogin");
        //osioAuthenticationFilter.setRememberMeServices(rememberMeServices()); //设置记住我
        //osioAuthenticationFilter.setUsernameParameter("id");
        //osioAuthenticationFilter.setPasswordParameter("password");
        return osioAuthenticationFilter;
    }

    @Bean
    public LogoutFilter osioLogoutFilter() {
        OsioLogoutFilter osioLogoutFilter = new OsioLogoutFilter(osioLogoutSuccessHandler(), osioLogoutHandler());
        osioLogoutFilter.setFilterProcessesUrl("/public/doLogout");
        return osioLogoutFilter;
    }

    @Bean
    public LogoutSuccessHandler osioLogoutSuccessHandler(){
        return new OsioLogoutSuccessHandler(osioService);
    }

    @Bean
    public LogoutHandler osioLogoutHandler(){
        return new OsioLogoutHandler();
    }
}