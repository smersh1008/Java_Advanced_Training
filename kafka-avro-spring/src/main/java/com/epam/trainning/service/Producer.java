package com.epam.trainning.service;

import com.epam.trainning.model.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    @Value("${topic.name}")
    private String topicName;

    private final KafkaTemplate<String, UserData> kafkaTemplate;

    public Producer(final KafkaTemplate<String, UserData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(final UserData userData) {
        final var future = kafkaTemplate.send(topicName, userData);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.printf("Sent message=[%s] with offset=[%s]%n.", userData, result.getRecordMetadata().offset());
            } else {
                System.out.printf("Unable to send message=[%s] due to : %s%n.", userData, ex.getMessage());
            }
        });
    }
}