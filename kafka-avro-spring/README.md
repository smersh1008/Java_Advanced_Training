## How to run

1. If Kafka is already installed, just update 'application.properties' file with the right Kafka URL (spring.kafka.bootstrap-servers property)
and Schema registry URL (spring.kafka.schema-registry property). 
2. If Kafka is missing on the environment, docker-compose.yml can be run by next command:
   1. docker-compose up
3. As soon as Kafka is launched (by default 'localhost:8080' should display Kafka UI), 
run 'mvn clean package' in the root folder of the project (it will create v1 Avro java file).
4. Run 'KafkaAvroSpringApplication.main()' method to start Spring Boot application. It will:
   1. Connect to Kafka by 'spring.kafka.bootstrap-servers' URL;
   2. Create Topic with name 'topic.name' (default is 'user-data') with 'topic.partitions-num' partition(s) (default is 1);
   3. Expose 'localhost:8079/user/publish' POST endpoint to publish UserData to Kafka.

## How to publish messages to Kafka

Send POST request to 'localhost:8079/user/publish' (for example by Postman) with the 'firstName' and 'lastName' parameters.
In the Spring Boot application console should be visible Producer and Consumer log messages.

## How to change schema version

1. Stop Spring Boot application; 
2. open 'pom.xml' file; 
3. navigate to 'avro-maven-plugin' configuration; 
4. update <include>user-v1.avsc</include> with <include>user-v2.avsc</include> (114 line of 'pom.xml');
5. run 'mvn clean package';
6. start Spring Boot application;
7. after v2 message is sent, on Kafka UI -> Schema Registry page 'user-data' schema version should be updated and
message itself should have 1 more property ("fired").