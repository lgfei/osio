package com.lgfei.osio.iam.biz.service.impl;

import com.lgfei.osio.iam.biz.model.IamUser;
import com.lgfei.osio.iam.biz.service.UserService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private JwtDecoder jwtDecoder;

    public UserServiceImpl(JwtDecoder jwtDecoder){
        this.jwtDecoder = jwtDecoder;
    }
    @Override
    public IamUser convertUser(String jwtStr) {
        if(jwtStr.startsWith("Bearer ")){
            jwtStr = jwtStr.substring(7);
        }
        Jwt jwt = jwtDecoder.decode(jwtStr);
        String sub = jwt.getClaim("sub");
        IamUser iamUser = new IamUser();
        iamUser.setName(sub);
        return iamUser;
    }
}
