package com.lgfei.osio.auth.biz.controller;

import com.lgfei.osio.starter.core.service.OsioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class HomeController {

    private final OsioService osioService;

    public HomeController(OsioService osioService){
        this.osioService = osioService;
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
        User principal  = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal:{}",principal);
        modelAndView.addObject("username", principal.getUsername());
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
