package com.lgfei.osio.iam.biz.service;

import com.lgfei.osio.iam.biz.model.IamUser;

public interface UserService {

    IamUser convertUser(String jwtStr);
}
