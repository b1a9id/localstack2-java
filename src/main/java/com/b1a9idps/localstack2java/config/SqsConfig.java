package com.b1a9idps.localstack2java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.b1a9idps.localstack2java.properties.SqsProperties;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration(proxyBeanMethods = false)
public class SqsConfig {

    @Bean
    public SqsClient sqsClient(SqsProperties properties) {
        var builder = SqsClient.builder()
                .region(Region.AP_NORTHEAST_1)
                .endpointOverride(properties.endpointUrl());
        if (properties.endpointUrl() != null) {
            builder.endpointOverride(properties.endpointUrl());
        }
        return builder.build();
    }
}
