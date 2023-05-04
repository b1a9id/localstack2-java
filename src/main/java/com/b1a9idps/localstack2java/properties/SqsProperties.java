package com.b1a9idps.localstack2java.properties;

import java.net.URI;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("settings.sqs")
public record SqsProperties(String queueName, URI endpointUrl) {
}
