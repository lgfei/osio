package com.lgfei.osio.auth.customsize;

import com.lgfei.osio.starter.core.service.OsioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OsioAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private OsioService osioService;

    public OsioAuthenticationFailureHandler(OsioService osioService){
        this.osioService = osioService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.sendRedirect(osioService.getBaseUrl() + "/public/login");
    }
}
