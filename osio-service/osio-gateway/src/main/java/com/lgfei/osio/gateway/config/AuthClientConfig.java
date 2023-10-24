package com.lgfei.osio.gateway.config;

import com.lgfei.osio.starter.core.service.OsioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class AuthClientConfig {

    private OsioService osioService;

    public AuthClientConfig(OsioService osioService){
        this.osioService = osioService;
    }

    @Bean
    public ReactiveClientRegistrationRepository reactiveClientRegistrationRepository() {
        return new InMemoryReactiveClientRegistrationRepository(this.githubClientRegistration(), this.myClientRegistration());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(
            OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

    private ClientRegistration githubClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("76c66047040c2f28055e")
                .clientSecret("315613192d7a28564ca181e4ee1a9e956114f4f9")
                .redirectUri(osioService.getGatewayUrl() + "/login/oauth2/code/github")
                .build();
    }

    private ClientRegistration myClientRegistration(){
        return ClientRegistration.withRegistrationId("osio")
                .clientId("osio-client-id")
                .clientSecret("osio-client-secret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(osioService.getGatewayUrl() + "/login/oauth2/code/osio")
                .scope("all")
                .clientName("osio-client-name")
                .authorizationUri(osioService.getGatewayUrl() + "/auth/oauth2/authorize")
                .tokenUri(osioService.getGatewayUrl() + "/auth/oauth2/token")
                .jwkSetUri(osioService.getGatewayUrl() + "/auth/oauth2/jwks")
                //.userInfoUri(osioService.getGatewayUrl() + "/iam/user/info")
                .userNameAttributeName("name")
                .build();
    }

}
