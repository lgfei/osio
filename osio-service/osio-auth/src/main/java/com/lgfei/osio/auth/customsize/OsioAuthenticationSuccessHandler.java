package com.lgfei.osio.auth.customsize;

import com.lgfei.osio.starter.core.service.OsioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OsioAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final OsioService osioService;
    private final ClientDetailsService clientDetailsService;

    public OsioAuthenticationSuccessHandler(OsioService osioService,
                                            ClientDetailsService clientDetailsService){
        this.osioService = osioService;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String clientId = request.getParameter("clientId");
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        String redirectUrl = osioService.getBaseUrl()
                + "/oauth/authorize?response_type=code"
                + "&client_id=" + clientDetails.getClientId()
                + "&scope=" + clientDetails.getScope().iterator().next()
                + "&redirect_uri=" + clientDetails.getRegisteredRedirectUri().iterator().next();
        response.sendRedirect(redirectUrl);
    }
}
