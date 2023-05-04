package com.b1a9idps.localstack2java;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.b1a9idps.localstack2java.properties.SqsProperties;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class SqsQueueMessageSender implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqsQueueMessageSender.class);

    private final SqsClient sqsClient;
    private final SqsProperties sqsProperties;

    public SqsQueueMessageSender(SqsClient sqsClient, SqsProperties sqsProperties) {
        this.sqsClient = sqsClient;
        this.sqsProperties = sqsProperties;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var getQueueUrlRequest = GetQueueUrlRequest.builder()
                .queueName(sqsProperties.queueName())
                .build();
        var queueUrl = sqsClient.getQueueUrl(getQueueUrlRequest).queueUrl();
        sendMessage(queueUrl);
        receiveMessage(queueUrl);
    }

    private void sendMessage(String queueUrl) {
        var sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody("test-message")
                .delaySeconds(5)
                .build();
        sqsClient.sendMessage(sendMessageRequest);
    }

    private void receiveMessage(String queueUrl) {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .build();
        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
        for (Message message : messages) {
            LOGGER.info("message is {}.", message.body());
        }
    }
}
