package com.epam.trainning.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value(value = "${topic.name}")
    private String topicName;
    @Value(value = "${topic.partitions-num}")
    private Integer partitions;
    @Value(value = "${topic.replication-factor}")
    private short replicationFactor;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic userTopic() {
        return new NewTopic(topicName, partitions, replicationFactor);
    }
}