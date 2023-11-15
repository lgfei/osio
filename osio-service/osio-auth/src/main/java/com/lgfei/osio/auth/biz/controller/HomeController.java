package com.lgfei.osio.auth.biz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/home")
    public ModelAndView home() {
        User principal  = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal:{}",principal);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", principal.getUsername());
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
