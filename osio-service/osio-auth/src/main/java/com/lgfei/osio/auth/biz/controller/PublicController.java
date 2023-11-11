package com.lgfei.osio.auth.biz.controller;

import com.lgfei.osio.starter.core.service.OsioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/public")
public class PublicController {
    private OsioService osioService;

    public PublicController(OsioService osioService){
        this.osioService = osioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
