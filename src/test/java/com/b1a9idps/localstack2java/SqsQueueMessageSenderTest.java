package com.b1a9idps.localstack2java;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.b1a9idps.localstack2java.config.TestConfig;
import com.b1a9idps.localstack2java.properties.SqsProperties;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("unittest")
@ContextConfiguration(classes = {TestConfig.class}, initializers = ConfigDataApplicationContextInitializer.class)
@Testcontainers
class SqsQueueMessageSenderTest extends AbstractContainerBaseTest {

    @Container
    final LocalStackContainer localStack = new LocalStackContainer(LOCALSTACK_IMAGE_NAME)
            .withServices(LocalStackContainer.Service.SQS);

    SqsQueueMessageSender sqsQueueMessageSender;
    SqsClient sqsClient;
    @Autowired
    SqsProperties sqsProperties;

    @BeforeEach
    void beforeEach() {
        sqsClient = SqsClient.builder()
                .endpointOverride(localStack.getEndpointOverride(LocalStackContainer.Service.SQS))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(localStack.getAccessKey(), localStack.getSecretKey())
                        )
                )
                .region(Region.of(localStack.getRegion()))
                .build();
        sqsClient.createQueue(builder -> builder.queueName(sqsProperties.queueName()));

        sqsQueueMessageSender = new SqsQueueMessageSender(sqsClient, sqsProperties);
    }

    @Test
    void sendMessage() {
        sqsQueueMessageSender.run(null);

        var queueUrl = sqsClient.getQueueUrl(builder -> builder.queueName(sqsProperties.queueName()))
                .queueUrl();

        var actual = sqsClient.receiveMessage(builder -> builder
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(10));
        Assertions.assertThat(actual.messages())
                .hasSize(1)
                .extracting(Message::body)
                .containsExactly("test-message");
    }

}
