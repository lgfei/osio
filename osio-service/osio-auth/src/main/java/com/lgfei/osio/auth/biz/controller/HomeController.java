package com.lgfei.osio.auth.biz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal:{}",principal);
        return "home";
    }
}
