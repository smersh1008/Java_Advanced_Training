package com.epam.trainning.springbootfirsttask.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Endpoint(id = "random-number")
@Component
public class RandomNumberActuatorEndpoint {
    private static final Random RANDOM = new Random();

    @ReadOperation
    public Map<String, Integer> randomNumberEndpoint() {
        return Map.of("number", RANDOM.nextInt(Integer.MAX_VALUE));
    }
}