package com.lgfei.osio.iam.biz.controller;

import com.lgfei.osio.iam.biz.model.IamUser;
import com.lgfei.osio.iam.biz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/info")
    public IamUser info(HttpServletRequest request){
        String jwt = request.getHeader("Authorization");
        System.out.println("jwt=" + jwt);
        return userService.convertUser(jwt);
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> current(@AuthenticationPrincipal OAuth2User oAuth2User){
        return ResponseEntity.ok(oAuth2User.getAttributes());
    }

}
