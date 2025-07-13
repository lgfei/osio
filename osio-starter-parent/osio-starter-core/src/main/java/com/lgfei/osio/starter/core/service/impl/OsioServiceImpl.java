package com.lgfei.osio.starter.core.service.impl;

import com.lgfei.osio.starter.core.properties.OsioProperties;
import com.lgfei.osio.starter.core.service.OsioService;
import org.springframework.util.StringUtils;

public class OsioServiceImpl implements OsioService {
    private static final String URL_SEPARATOR = "/";
    private final OsioProperties osioProperties;

    public OsioServiceImpl(OsioProperties osioProperties){
        this.osioProperties = osioProperties;
    }

    @Override
    public String getBaseUrl() {
        if(null == osioProperties){
            return null;
        }
        String gatewayUrl = this.urlTolerantHandle(osioProperties.getGatewayUrl());
        String routePath = this.urlTolerantHandle(osioProperties.getRoutePath());
        if(StringUtils.hasLength(routePath)){
            if(routePath.startsWith(URL_SEPARATOR)){
                return gatewayUrl + routePath;
            }else{
                return gatewayUrl + URL_SEPARATOR + routePath;
            }
        }
        return osioProperties.getGatewayUrl();
    }

    private String urlTolerantHandle(String url){
        if(StringUtils.hasLength(url)){
            if(url.endsWith(URL_SEPARATOR)){
                url = url.substring(0, url.length() - 1);
                urlTolerantHandle(url);
            }
        }
        return url;
    }
}
