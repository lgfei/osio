package com.lgfei.osio.auth.customsize;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class OsioLogoutFilter extends LogoutFilter {

    public OsioLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers){
        super(logoutSuccessHandler, handlers);
    }
}
