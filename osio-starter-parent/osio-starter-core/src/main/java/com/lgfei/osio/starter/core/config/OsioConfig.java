package com.lgfei.osio.starter.core.config;

import com.lgfei.osio.starter.core.properties.OsioProperties;
import com.lgfei.osio.starter.core.service.OsioService;
import com.lgfei.osio.starter.core.service.impl.OsioServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OsioProperties.class)
@ConditionalOnClass(OsioService.class)
public class OsioConfig {

    private OsioProperties osioProperties;

    public OsioConfig(OsioProperties osioProperties){
        this.osioProperties = osioProperties;
    }

    @Bean
    public OsioService osioService(){
        return new OsioServiceImpl(osioProperties);
    }
}
