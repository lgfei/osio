package com.lgfei.osio.auth.biz.controller;

import com.lgfei.osio.starter.core.service.OsioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/public")
@SessionAttributes("authorizationRequest")
public class PublicController {
    private final OsioService osioService;


    public PublicController(OsioService osioService){
        this.osioService = osioService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clientId", "osio-client-id");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/confirm_access")
    public ModelAndView confirmAccess(Map<String, Object> model) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clientId", authorizationRequest.getClientId());
        Set<String> scopes = authorizationRequest.getScope();
        if(!CollectionUtils.isEmpty(scopes)){
            modelAndView.addObject("scopes", "scope." + scopes.iterator().next());
        }
        modelAndView.setViewName("confirm_access");
        return modelAndView;
    }
}
