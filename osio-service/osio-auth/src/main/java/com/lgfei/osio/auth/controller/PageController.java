package com.lgfei.osio.auth.controller;

import com.lgfei.osio.starter.core.service.OsioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Set;

/**
 * @author lgfei
 * @date 2025/7/16 16:27
 */
@Slf4j
@Controller
@RequestMapping("/page")
public class PageController {
    private final OsioService osioService;
    private final RegisteredClientRepository registeredClientRepository;

    public PageController(OsioService osioService,
                          RegisteredClientRepository registeredClientRepository)
    {
        this.osioService = osioService;
        this.registeredClientRepository = registeredClientRepository;
    }

    @GetMapping("/login")
    public String index() {
        return "login";
    }

    @GetMapping("/consent")
    public String index(HttpServletRequest request,
                        Principal principal,
                        @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                        @RequestParam(OAuth2ParameterNames.STATE) String state)
    {
        String principalName = principal.getName();
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
        String clientName = registeredClient.getClientName();
        String redirectUri = registeredClient.getRedirectUris().iterator().next();
        Set<String> scopes = registeredClient.getScopes();
        request.setAttribute("principalName", principalName);
        request.setAttribute("clientName", clientName);
        request.setAttribute("clientId", clientId);
        request.setAttribute("state", state);
        request.setAttribute("scopes", scopes);
        return "consent";
    }

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        String error = request.getParameter("error");
        log.info("error:{}", error);
        ModelAndView modelAndView = new ModelAndView();
        if(!StringUtils.isEmpty(error)){
            modelAndView.setViewName("redirect:" + osioService.getBaseUrl() + "/public/login?message="+error);
            return modelAndView;
        }
        String code = request.getParameter("code");
        log.info("code:{}", code);
        User principal  = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal:{}",principal);
        modelAndView.addObject("username", principal.getUsername());
        modelAndView.setViewName("home");
        return modelAndView;
    }

//    @GetMapping("/.well-known/openid-configuration")
//    public ResponseEntity<String> debug() {
//        return ResponseEntity.ok("OIDC endpoint is reachable");
//    }
}
