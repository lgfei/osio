package com.lgfei.osio.auth.config;

import com.lgfei.osio.starter.core.service.OsioService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final OsioService osioService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenStore tokenStore;
    private final ClientDetailsService clientDetailsService;
    private final JwtAccessTokenConverter accessTokenEnhancer;

    public AuthServerConfig(OsioService osioService,
                            PasswordEncoder passwordEncoder,
                            AuthenticationManager authenticationManager,
                            UserDetailsService userDetailsService,
                            TokenStore tokenStore,
                            ClientDetailsService clientDetailsService,
                            JwtAccessTokenConverter accessTokenEnhancer)
    {
        this.osioService = osioService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
        this.clientDetailsService = clientDetailsService;
        this.accessTokenEnhancer = accessTokenEnhancer;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .passwordEncoder(passwordEncoder)
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("osio-client-id")
                .secret(passwordEncoder.encode("osio-client-secret"))
                .resourceIds("order")
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                .scopes("all")
                .autoApprove(false)
                .redirectUris(osioService.getBaseUrl() + "/home");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenServices(tokenServices())
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCodeServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                .pathMapping("/oauth/confirm_access","/public/confirm_access")
        ;
    }

    AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setTokenEnhancer(accessTokenEnhancer);
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }

    AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
}
