package com.lgfei.osio.auth.customsize;

import com.lgfei.osio.starter.core.service.OsioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OsioLogoutSuccessHandler implements LogoutSuccessHandler {
    private OsioService osioService;

    public OsioLogoutSuccessHandler(OsioService osioService){
        this.osioService = osioService;
    }
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.sendRedirect(osioService.getBaseUrl() + "/public/login");
    }
}
