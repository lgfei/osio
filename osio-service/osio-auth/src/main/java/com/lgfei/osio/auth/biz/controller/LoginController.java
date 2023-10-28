package com.lgfei.osio.auth.biz.controller;

import com.lgfei.osio.starter.core.service.OsioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private OsioService osioService;

    public LoginController(OsioService osioService){
        this.osioService = osioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/doLogin")
    public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:" + osioService.getGatewayUrl() + "/auth/home");
        return modelAndView;
    }
}
