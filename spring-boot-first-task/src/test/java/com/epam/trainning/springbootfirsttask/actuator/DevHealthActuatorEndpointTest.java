package com.epam.trainning.springbootfirsttask.actuator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "dev")
class DevHealthActuatorEndpointTest {
    private static final String HEALTH_ENDPOINT = "/actuator/health";
    private static final String URL_MASK = "http://localhost:%d%s";

    @Value("${local.management.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Health Actuator endpoint detailed info is missing for 'dev' not authorized profile.")
    void healthActuatorEndpointInfoIsMissingForDevProfile() {
        final var entity = this.testRestTemplate.getForEntity(String.format(URL_MASK, this.port, HEALTH_ENDPOINT), Map.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assertions.assertTrue(entity.hasBody());
        Assertions.assertNotNull(entity.getBody());
        Assertions.assertNull(entity.getBody().get("components"));
    }

    @Test
    @DisplayName("Health Actuator endpoint detailed info is present for 'dev' profile when authorized.")
    void healthActuatorEndpointInfoIsPresentForDevProfileWhenAuthorized() {
        final var requestHeaders = new HttpHeaders();
        requestHeaders.setBasicAuth("admin", "admin");
        final var request = new HttpEntity<String>(requestHeaders);
        final var entity = this.testRestTemplate.exchange(String.format(URL_MASK, this.port, HEALTH_ENDPOINT), HttpMethod.GET, request, Map.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assertions.assertTrue(entity.hasBody());
        Assertions.assertNotNull(entity.getBody());
        Assertions.assertNotNull(entity.getBody().get("components"));
    }
}