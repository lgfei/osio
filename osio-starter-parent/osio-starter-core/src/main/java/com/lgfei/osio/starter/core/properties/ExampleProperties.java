package com.lgfei.osio.starter.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "example")
@Data
public class ExampleProperties {

    private String who;
    private Date when;
    private String where;
    private String what;
    private String why;

}
