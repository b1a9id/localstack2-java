# localstack2-java
LocalStack 2.0を利用したJava(Spring Boot)アプリケーション。Java 17が必要です。

起動方法
```shell
docker-compose up -d
aws --endpoint-url="http://localhost:4566" sqs create-queue --queue-name sample-queue
./gradlew clean bootRun --args='--spring.profiles.active=local'
```
