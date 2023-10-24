package com.lgfei.osio.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OsioRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsioRegisterApplication.class, args);
    }

}
