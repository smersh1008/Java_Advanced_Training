package com.epam.trainning.springbootfirsttask.actuator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "local")
class RandomNumberActuatorEndpointTest {
    private static final String RANDOM_NUMBER_ENDPOINT = "/actuator/random-number";
    @Value("${local.management.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("'random-number' custom Actuator endpoint returns 200 and has 'number' field in the body.")
    void randomNumberCustomActuatorEndpointReturns200andHasCorrectBody() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + RANDOM_NUMBER_ENDPOINT, Map.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assertions.assertTrue(entity.hasBody());
        Assertions.assertNotNull(entity.getBody());
        Assertions.assertNotNull(entity.getBody().get("number"));
    }
}