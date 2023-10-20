package com.epam.trainning.controller;

import com.epam.trainning.model.UserData;
import com.epam.trainning.service.Producer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class KafkaController {
    private final Producer producer;

    KafkaController(final Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        this.producer.sendMessage(UserData.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build());
    }
}