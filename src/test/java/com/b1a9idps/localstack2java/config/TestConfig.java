package com.b1a9idps.localstack2java.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;

import com.b1a9idps.localstack2java.properties.SqsProperties;

@TestConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties(value = SqsProperties.class)
public class TestConfig {
}
