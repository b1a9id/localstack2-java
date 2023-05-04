package com.b1a9idps.localstack2java;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.b1a9idps.localstack2java.properties.SqsProperties;

import software.amazon.awssdk.services.sqs.SqsClient;

@Component
public class SqsQueueMessageSender implements ApplicationRunner {

    static final String DEFAULT_MESSAGE = "test-message";

    private final SqsClient sqsClient;
    private final SqsProperties sqsProperties;

    public SqsQueueMessageSender(SqsClient sqsClient, SqsProperties sqsProperties) {
        this.sqsClient = sqsClient;
        this.sqsProperties = sqsProperties;
    }

    @Override
    public void run(ApplicationArguments args) {
        var queueUrl = sqsClient.getQueueUrl(builder -> builder.queueName(sqsProperties.queueName()))
                .queueUrl();
        sqsClient.sendMessage(builder -> builder.queueUrl(queueUrl).messageBody(DEFAULT_MESSAGE));
    }
}
