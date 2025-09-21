package com.lgfei.osio.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.PasswordComparisonAuthenticator;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;

@Configuration
public class LdapConfig {

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource source = new LdapContextSource();
        source.setUrl("ldap://localhost:389");
        source.setBase("dc=lgfei,dc=com");
        source.setUserDn("cn=admin,dc=lgfei,dc=com");
        source.setPassword("admin");
        
        // Add connection pool and timeout settings
        source.setPooled(true);
        source.afterPropertiesSet();
        return source;
    }

    @Bean
    public AuthenticationProvider ldapAuthenticationProvider() {
        // 使用管理员凭据进行用户搜索，然后用用户凭据进行绑定认证
        BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource());
        
        // 使用管理员身份进行用户搜索
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(
            "ou=users", "(uid={0})", contextSource());
        bindAuthenticator.setUserSearch(userSearch);
        
        DefaultLdapAuthoritiesPopulator authoritiesPopulator =
                new DefaultLdapAuthoritiesPopulator(contextSource(), null);
        authoritiesPopulator.setIgnorePartialResultException(true);
        authoritiesPopulator.setSearchSubtree(true);
        
        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(bindAuthenticator, authoritiesPopulator);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }
    
    // 备选方法：如果绑定认证不工作，可以尝试密码比较认证
    // @Bean
    public AuthenticationProvider ldapPasswordComparisonAuthenticationProvider() {
        PasswordComparisonAuthenticator authenticator = new PasswordComparisonAuthenticator(contextSource());
        authenticator.setUserDnPatterns(new String[]{"uid={0},ou=users,dc=lgfei,dc=com"});
        authenticator.setPasswordAttributeName("userPassword");
        authenticator.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        
        DefaultLdapAuthoritiesPopulator authoritiesPopulator =
                new DefaultLdapAuthoritiesPopulator(contextSource(), null);
        authoritiesPopulator.setIgnorePartialResultException(true);
        
        return new LdapAuthenticationProvider(authenticator, authoritiesPopulator);
    }
}

