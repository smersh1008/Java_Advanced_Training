package com.epam.trainning.service;

import com.epam.trainning.model.UserData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @KafkaListener(topics = "${topic.name}", groupId = "${consumer.group-id}")
    public void consume(ConsumerRecord<String, UserData> record) {
        System.out.printf("Consumed message -> %s%n", record.value());
    }
}