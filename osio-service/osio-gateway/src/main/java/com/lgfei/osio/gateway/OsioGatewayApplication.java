package com.lgfei.osio.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class OsioGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsioGatewayApplication.class, args);
    }

}
