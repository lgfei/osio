package com.lgfei.osio.auth.config;

import com.lgfei.osio.starter.core.service.OsioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OsioAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final OsioService osioService;

    public OsioAuthenticationSuccessHandler(OsioService osioService){
        this.osioService = osioService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //response.sendRedirect(osioService.getBaseUrl() + "/home");
        response.sendRedirect(osioService.getBaseUrl() + "/oauth/authorize?response_type=code&client_id=osio-client-id&scope=all&redirect_uri=http://osio-api-dev.lgfei.com/auth/home");
    }
}
