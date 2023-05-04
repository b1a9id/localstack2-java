package com.b1a9idps.localstack2java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(value = {"com.b1a9idps.localstack2java.properties"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.exit(SpringApplication.run(Application.class, args));
    }

}
