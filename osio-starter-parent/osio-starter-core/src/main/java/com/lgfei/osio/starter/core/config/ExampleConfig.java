package com.lgfei.osio.starter.core.config;

import com.lgfei.osio.starter.core.properties.ExampleProperties;
import com.lgfei.osio.starter.core.service.ExampleService;
import com.lgfei.osio.starter.core.service.impl.ExampleServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(ExampleProperties.class)
@ConditionalOnClass(ExampleService.class)
//@ConditionalOnProperty(
//        prefix = "example",
//        name = "enable",
//        havingValue = "true"
//)
public class ExampleConfig {
    private Environment environment;
    private ExampleProperties exampleProperties;

    public ExampleConfig(Environment environment, ExampleProperties exampleProperties){
        this.environment = environment;
        this.exampleProperties = exampleProperties;
    }

    @Bean
    public ExampleService exampleService(){
        return new ExampleServiceImpl(environment, exampleProperties);
    }
}
