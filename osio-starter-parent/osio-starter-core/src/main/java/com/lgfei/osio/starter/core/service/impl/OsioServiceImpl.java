package com.lgfei.osio.starter.core.service.impl;

import com.lgfei.osio.starter.core.properties.OsioProperties;
import com.lgfei.osio.starter.core.service.OsioService;

public class OsioServiceImpl implements OsioService {

    private OsioProperties osioProperties;

    public OsioServiceImpl(OsioProperties osioProperties){
        this.osioProperties = osioProperties;
    }

    @Override
    public String getGatewayUrl() {
        if(null == osioProperties){
            return null;
        }
        return osioProperties.getGatewayUrl();
    }
}
