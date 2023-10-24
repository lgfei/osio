package com.lgfei.osio.starter.core.service.impl;

import com.lgfei.osio.starter.core.properties.ExampleProperties;
import com.lgfei.osio.starter.core.service.ExampleService;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class ExampleServiceImpl implements ExampleService {
    private Environment environment;
    private ExampleProperties exampleProperties;

    public ExampleServiceImpl(Environment environment, ExampleProperties exampleProperties){
        this.environment = environment;
        this.exampleProperties = exampleProperties;
    }

    @Override
    public String doSomeThing() throws UnknownHostException {
        String who = StringUtils.hasLength(this.exampleProperties.getWho())
                ? this.exampleProperties.getWho() : environment.getProperty("spring.application.name");
        Date when = null != this.exampleProperties.getWhen()
                ? this.exampleProperties.getWhen() : new Date();
        String where = StringUtils.hasLength(this.exampleProperties.getWhere())
                ? this.exampleProperties.getWhere() : InetAddress.getLocalHost().toString();
        String what = StringUtils.hasLength(this.exampleProperties.getWhat())
                ? this.exampleProperties.getWhat() : "do some thing";
        String why = StringUtils.hasLength(this.exampleProperties.getWhy())
                ? this.exampleProperties.getWhy() : "test spring boot starter";
        return who + ">>>" + when + ">>>" + where + ">>>" + what + ">>>" + why;
    }

}
