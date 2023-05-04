package com.b1a9idps.localstack2java;

import org.testcontainers.utility.DockerImageName;

public abstract class AbstractContainerBaseTest {
    protected static final DockerImageName LOCALSTACK_IMAGE_NAME = DockerImageName.parse("localstack/localstack:2.0.2");
}
